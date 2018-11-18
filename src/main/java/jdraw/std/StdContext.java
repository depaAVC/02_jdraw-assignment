/*
 * Copyright (c) 2018 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved.
 */
package jdraw.std;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import jdraw.figures.GroupFigure;
import jdraw.figures.LineTool;
import jdraw.figures.OvalTool;
import jdraw.figures.RectTool;
import jdraw.framework.DrawCommandHandler;
import jdraw.framework.DrawModel;
import jdraw.framework.DrawTool;
import jdraw.framework.DrawToolFactory;
import jdraw.framework.DrawView;
import jdraw.framework.Figure;
import jdraw.grid.FixedGrid;

/**
 * Standard implementation of interface DrawContext.
 * 
 * @see DrawView
 * @author Dominik Gruntz & Christoph Denzler
 * @version 2.6, 24.09.09
 */
@SuppressWarnings("serial")
public class StdContext extends AbstractContext {

	/**
	 * stores Figures which are either copied or cut out from the Views.
	 * Ref: Uebung07.
	 */
	private List<Figure> clipBoard = new ArrayList<>();

	/**
	 * Constructs a standard context with a default set of drawing tools.
	 * @param view the view that is displaying the actual drawing.
	 */
	public StdContext(DrawView view) {
		super(view, null);
	}

	/**
	 * Constructs a standard context. The drawing tools available can be
	 * parameterized using <code>toolFactories</code>.
	 * 
	 * @param view  the view that is displaying the actual drawing.
	 * @param toolFactories  a list of DrawToolFactories that are available to the user
	 */
	public StdContext(DrawView view, List<DrawToolFactory> toolFactories) {
		super(view, toolFactories);
	}

	/**
	 * Creates and initializes the "Edit" menu.
	 * 
	 * @return the new "Edit" menu.
	 */
	@Override
	protected JMenu createEditMenu() {
		JMenu editMenu = new JMenu("Edit");
		final JMenuItem undo = new JMenuItem("Undo");
		undo.setAccelerator(KeyStroke.getKeyStroke("control Z"));
		editMenu.add(undo);
		undo.addActionListener(e -> {
				final DrawCommandHandler h = getModel().getDrawCommandHandler();
				if (h.undoPossible()) {
					h.undo();
				}
			}
		);

		final JMenuItem redo = new JMenuItem("Redo");
		redo.setAccelerator(KeyStroke.getKeyStroke("control Y"));
		editMenu.add(redo);
		redo.addActionListener(e -> {
				final DrawCommandHandler h = getModel().getDrawCommandHandler();
				if (h.redoPossible()) {
					h.redo();
				}
			}
		);
		editMenu.addSeparator();

		JMenuItem sa = new JMenuItem("SelectAll");
		sa.setAccelerator(KeyStroke.getKeyStroke("control A"));
		editMenu.add(sa);
		sa.addActionListener( e -> {
				for (Figure f : getModel().getFigures()) {
					getView().addToSelection(f);
				}
				getView().repaint();
			}
		);

		editMenu.addSeparator();
		JMenuItem cutItem = new JMenuItem("Cut");
		JMenuItem copyItem = new JMenuItem("Copy");
		JMenuItem pasteItem = new JMenuItem("Paste");
		cutItem.addActionListener(e -> {
			//bringToFront(getView().getModel(), getView().getSelection());
			System.out.println("cut");
			clipBoard.clear();
			List<Figure> originals = getView().getSelection(); //Frage: Variable überhaupt notwendig?
			for (Figure f : originals) {
				System.out.println("------------- Start ------------");
				System.out.println("Figure: " + f);
				System.out.println("Clipboard" + clipBoard.size());
				System.out.println(getView().getSelection().size());

				Figure cf = f/*.clone()*/;	//cloning not necessary bcs f gets removed from model anyway.
				clipBoard.add(cf);						//saving a prototype.
				getView().removeFromSelection(f);
				getView().getModel().removeFigure(f);	//removing from the view. View gets notified (observer pattern).

				System.out.println("Clipboard" + clipBoard.size());
				System.out.println(getView().getSelection().size());
				System.out.println("------------- End ------------");
			}
			//getView().clearSelection();
		});
		copyItem.addActionListener(e -> {
			//bringToFront(getView().getModel(), getView().getSelection());
			System.out.println("copy");
			clipBoard.clear();
			List<Figure> originals = getView().getSelection(); //Frage: Variable überhaupt notwendig?
			for (Figure f : originals) {
				clipBoard.add(f.clone()); //saving a prototype.

				System.out.println("\n f != f.clone()" + (f != f.clone()));
				System.out.println("f != f.clone()" + (f != f.clone()));
				System.out.println("f.Bounds().x == f.clone().getBounds().x" + (f.getBounds().getX() == f.clone().getBounds().getX()));
				System.out.println("f.Bounds().y == f.clone().getBounds().y" + (f.getBounds().getY() == f.clone().getBounds().getY()));
			}
		});
		pasteItem.addActionListener(e -> {
			//bringToFront(getView().getModel(), getView().getSelection());
			System.out.println("paste");
			getView().clearSelection();		//allows to paste while other figure is still selected.
			if (!clipBoard.isEmpty()) {
				for (Figure prototypeF : clipBoard) {
					Figure cf = prototypeF.clone();
					getView().addToSelection(cf);
					// draws the cf in the view and registers observer.
					// Note: not sure if copying references to observers via prototypeF.clone()
					// overlaps with getModel().addfigure(cf), resulting in duplicate references?
					getView().getModel().addFigure(cf);
				}
			}
		});
		editMenu.add(cutItem);
		editMenu.add(copyItem);
		editMenu.add(pasteItem);

		editMenu.addSeparator();
		JMenuItem clear = new JMenuItem("Clear");
		editMenu.add(clear);
		clear.addActionListener(e -> {
			getModel().removeAllFigures();
		});
		
		editMenu.addSeparator();
		JMenuItem group = new JMenuItem("Group");
		group.addActionListener(e -> {
			List<Figure> selection = getView().getSelection();
			for(Figure f : selection) {
				getModel().removeFigure(f);
			}
			getModel().addFigure(new GroupFigure(selection));
		});
		getView().getSelection();
		editMenu.add(group);

		JMenuItem ungroup = new JMenuItem("Ungroup");
		ungroup.addActionListener(e -> {
			List<Figure> selection = getView().getSelection();
			for(Figure f : selection) {
				if (f instanceof GroupFigure) {
					Iterable<Figure> parts = ((GroupFigure) f).getFigureParts();
					for(Figure fig : parts) {
						getModel().addFigure(fig);
					}
					getModel().removeFigure(f);
				}
			}

		});
		editMenu.add(ungroup);

		editMenu.addSeparator();

		JMenu orderMenu = new JMenu("Order...");
		JMenuItem frontItem = new JMenuItem("Bring To Front");
		frontItem.addActionListener(e -> {
			bringToFront(getView().getModel(), getView().getSelection());
		});
		orderMenu.add(frontItem);
		JMenuItem backItem = new JMenuItem("Send To Back");
		backItem.addActionListener(e -> {
			sendToBack(getView().getModel(), getView().getSelection());
		});
		orderMenu.add(backItem);
		editMenu.add(orderMenu);

		JMenu grid = new JMenu("Grid...");
		editMenu.add(grid);
		JMenuItem grid20Px = new JMenuItem("Grid 20 Px");
		JMenuItem grid50Px = new JMenuItem("Grid 50 Px");
		JMenuItem resetGrid = new JMenuItem("Reset Grid");

		grid20Px.addActionListener(e -> getView().setGrid( new FixedGrid(20,20) ));
		grid50Px.addActionListener(e -> getView().setGrid( new FixedGrid(50, 50) ));
		resetGrid.addActionListener(e -> getView().setGrid(null));

		grid.add(grid20Px);
		grid.add(grid50Px);
		grid.add(resetGrid);
		
		return editMenu;
	}

	/**
	 * Creates and initializes items in the file menu.
	 * 
	 * @return the new "File" menu.
	 */
	@Override
	protected JMenu createFileMenu() {
		JMenu fileMenu = new JMenu("File");
		JMenuItem open = new JMenuItem("Open");
		fileMenu.add(open);
		open.setAccelerator(KeyStroke.getKeyStroke("control O"));
		open.addActionListener(e -> doOpen());

		JMenuItem save = new JMenuItem("Save");
		save.setAccelerator(KeyStroke.getKeyStroke("control S"));
		fileMenu.add(save);
		save.addActionListener(e ->	doSave());

		JMenuItem exit = new JMenuItem("Exit");
		fileMenu.add(exit);
		exit.addActionListener(e -> System.exit(0));
		
		return fileMenu;
	}

	@Override
	protected void doRegisterDrawTools() {
		// TODO Add new figure tools here
		DrawTool ovalTool = new OvalTool(this);
		addTool(ovalTool);
		DrawTool lineTool = new LineTool(this);
		addTool(lineTool);
		DrawTool rectangleTool = new RectTool(this);
		addTool(rectangleTool);
	}

	/**
	 * Changes the order of figures and moves the figures in the selection
	 * to the front, i.e. moves them to the end of the list of figures.
	 * @param model model in which the order has to be changed
	 * @param selection selection which is moved to front
	 */
	public void bringToFront(DrawModel model, List<Figure> selection) {
		// the figures in the selection are ordered according to the order in
		// the model
		List<Figure> orderedSelection = new LinkedList<Figure>();
		int pos = 0;
		for (Figure f : model.getFigures()) {
			pos++;
			if (selection.contains(f)) {
				orderedSelection.add(0, f);
			}
		}
		for (Figure f : orderedSelection) {
			model.setFigureIndex(f, --pos);
		}
	}

	/**
	 * Changes the order of figures and moves the figures in the selection
	 * to the back, i.e. moves them to the front of the list of figures.
	 * @param model model in which the order has to be changed
	 * @param selection selection which is moved to the back
	 */
	public void sendToBack(DrawModel model, List<Figure> selection) {
		// the figures in the selection are ordered according to the order in
		// the model
		List<Figure> orderedSelection = new LinkedList<Figure>();
		for (Figure f : model.getFigures()) {
			if (selection.contains(f)) {
				orderedSelection.add(f);
			}
		}
		int pos = 0;
		for (Figure f : orderedSelection) {
			model.setFigureIndex(f, pos++);
		}
	}

	/**
	 * Handles the saving of a drawing to a file.
	 */
	private void doSave() {
		JFileChooser chooser = new JFileChooser(getClass().getResource("").getFile());
		chooser.setDialogTitle("Save Graphic");
		chooser.setDialogType(JFileChooser.SAVE_DIALOG);
		
		chooser.setFileFilter(new FileNameExtensionFilter("JDraw Graphics (*.draw)", "draw"));
		chooser.addChoosableFileFilter(new FileNameExtensionFilter("JDraw Graphics (*.xml)", "xml"));
		chooser.addChoosableFileFilter(new FileNameExtensionFilter("JDraw Graphics (*.json)", "json"));
		
		int res = chooser.showSaveDialog(this);

		if (res == JFileChooser.APPROVE_OPTION) {
			// save graphic
			File file = chooser.getSelectedFile();
			FileFilter filter = chooser.getFileFilter();
			if(filter instanceof FileNameExtensionFilter && !filter.accept(file)) {
				file = new File(chooser.getCurrentDirectory(), file.getName() + "." + ((FileNameExtensionFilter)filter).getExtensions()[0]);

				//How to Outputstream: https://www.tutorialspoint.com/java/io/objectoutputstream_writeobject.htm
				try {
					FileOutputStream fos = new FileOutputStream(file);
					ObjectOutputStream oos = new ObjectOutputStream(fos);

					Iterable<Figure> allFigures = getModel().getFigures();
					//required info for ObjectInputStream
					int streamLength = ((List<Figure>) allFigures).size();
					oos.writeInt(streamLength);
					for(Figure f : allFigures) {
						oos.writeObject(f.clone());
					}
					oos.close();
					fos.close();
				} catch (FileNotFoundException ffe) {
					System.out.println(ffe.getStackTrace());
				} catch (IOException ioe) {
					System.out.println(ioe.getStackTrace());
				}
			}
			System.out.println("save current graphic to file " + file.getName() + " using format "
					+ ((FileNameExtensionFilter)filter).getExtensions()[0]);
		}
	}

	/**
	 * Handles the opening of a new drawing from a file.
	 */
	private void doOpen() {
		JFileChooser chooser = new JFileChooser(getClass().getResource("")
				.getFile());
		chooser.setDialogTitle("Open Graphic");
		chooser.setDialogType(JFileChooser.OPEN_DIALOG);
		chooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
			@Override
			public String getDescription() {
				return "JDraw Graphic (*.draw)";
			}

			@Override
			public boolean accept(File f) {
				return f.isDirectory() || f.getName().endsWith(".draw");
			}
		});
		int res = chooser.showOpenDialog(this);

		if (res == JFileChooser.APPROVE_OPTION) {
			// read jdraw graphic
			System.out.println("read file "
					+ chooser.getSelectedFile().getName());

			//How to Inputstream: https://www.tutorialspoint.com/java/io/objectoutputstream_writeobject.htm
			try {
				FileInputStream fis = new FileInputStream( chooser.getSelectedFile().getAbsolutePath() );
				ObjectInputStream ois = new ObjectInputStream(fis);

				int streamLength = ois.readInt();
				for(int i = 0; i < streamLength; i++ ) {
					getModel().addFigure( (Figure) ois.readObject() );
				}

				ois.close();
				fis.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

}
