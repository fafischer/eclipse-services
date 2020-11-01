/*
* ObjectListView.java
*   
* Copyright 2009 - 2015 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-model-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package de.te2m.eclipse.service.views;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.DrillDownAdapter;
import org.eclipse.ui.part.ViewPart;

import de.te2m.eclipse.service.model.tree.AbstractParentNode;
import de.te2m.eclipse.service.model.tree.AttributedTreeParentNode;
import de.te2m.eclipse.service.model.tree.TreeNode;
import de.te2m.eclipse.service.model.tree.bo.ObjectViewRootNode;
import de.te2m.eclipse.service.model.tree.service.ParameterNode;
import de.te2m.eclipse.service.model.tree.service.ProjectNode;
import de.te2m.eclipse.service.model.tree.service.RetValNode;
import de.te2m.eclipse.service.views.actions.ManageBOAction;
import de.te2m.eclipse.service.views.actions.common.EditDescriptionAction;
import de.te2m.eclipse.service.views.actions.common.RefreshAction;
import de.te2m.eclipse.service.views.actions.project.LoadProjectAction;

/**
 * The Class ObjectListView.
 *
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public class ObjectListView extends ViewPart {

	/**
	 * The Class NameSorter.
	 * 
	 * @author ffischer
	 * @version 1.0
	 * @since 1.0
	 */
	class NameSorter extends ViewerSorter {
	}

	/**
	 * The Class ViewContentProvider.
	 * 
	 * @author ffischer
	 * @version 1.0
	 * @since 1.0
	 */
	class ViewContentProvider implements IStructuredContentProvider,
			ITreeContentProvider {

		/**
		 * The invisible root.
		 */
		private ObjectViewRootNode invisibleRoot;

		/**
		 * Instantiates a new view content provider.
		 * 
		 * @param invisibleRoot
		 *            the invisible root
		 */
		public ViewContentProvider(ObjectViewRootNode invisibleRoot) {
			super();
			this.invisibleRoot = invisibleRoot;

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
		 */
		public void dispose() {
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang
		 * .Object)
		 */
		public Object[] getChildren(Object parent) {
			if (parent instanceof AbstractParentNode) {
				return ((AbstractParentNode) parent).getChildren();
			}
			return new Object[0];
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.eclipse.jface.viewers.IStructuredContentProvider#getElements(
		 * java.lang.Object)
		 */
		public Object[] getElements(Object parent) {
			if (parent.equals(getViewSite())) {
				if (invisibleRoot == null)
					initialize();
				return getChildren(invisibleRoot);
			}
			return getChildren(parent);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang
		 * .Object)
		 */
		public Object getParent(Object child) {
			if (child instanceof TreeNode) {
				return ((TreeNode) child).getParent();
			}
			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang
		 * .Object)
		 */
		public boolean hasChildren(Object parent) {
			if (parent instanceof AbstractParentNode)
				return ((AbstractParentNode) parent).hasChildren();
			return false;
		}

		/*
		 * We will set up a dummy model to initialize tree heararchy. In a real
		 * code, you will connect to a real model and expose its hierarchy.
		 */
		/**
		 * Initialize.
		 */
		private void initialize() {
			if (null == invisibleRoot) {
				invisibleRoot = new ObjectViewRootNode("root");
			}

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse
		 * .jface.viewers.Viewer, java.lang.Object, java.lang.Object)
		 */
		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		}
	}

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "de.te2m.eclipse.service.views.ObjectListView";

	/**
	 * The invisible root.
	 */
	private ObjectViewRootNode invisibleRoot;

	/**
	 * The viewer.
	 */
	private TreeViewer viewer;
	
	/**
	 * The drill down adapter.
	 */
	private DrillDownAdapter drillDownAdapter;
	
	/**
	 * The refresh action.
	 */
	private Action refreshAction;

	
	/**
	 * The edit description action.
	 */
	private Action editDescriptionAction;

	/**
	 * The load action.
	 */
	private Action loadAction;

	/**
	 * The manage bo action.
	 */
	private Action manageBOAction;

	/**
	 * The constructor.
	 */
	public ObjectListView() {

		invisibleRoot = new ObjectViewRootNode("HiddenRoot");

	}

	/**
	 * Contribute to action bars.
	 */
	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 * 
	 * @param parent
	 *            the parent
	 */
	public void createPartControl(Composite parent) {
		viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		drillDownAdapter = new DrillDownAdapter(viewer);
		viewer.setContentProvider(new ViewContentProvider(invisibleRoot));
		viewer.setLabelProvider(new DefaultTreeViewLabelProvider());
		viewer.setSorter(new NameSorter());
		viewer.setInput(getViewSite());

		// Create the help context id for the viewer's control
		PlatformUI
				.getWorkbench()
				.getHelpSystem()
				.setHelp(viewer.getControl(),
						"de-te2m-eclipse-generator.viewer");
		makeActions();
		hookContextMenu();
		hookDoubleClickAction();
		contributeToActionBars();

		ProjectModelProvider.getInstance().registerView(viewer);
	}

	/**
	 * Fill context menu.
	 * 
	 * @param manager
	 *            the manager
	 */
	private void fillContextMenu(IMenuManager manager) {

		if (viewer.getSelection().isEmpty()) {
			return;
		}

		if (viewer.getSelection() instanceof IStructuredSelection) {
			IStructuredSelection selection = (IStructuredSelection) viewer
					.getSelection();
			Object object = selection.getFirstElement();
			
			if (object instanceof TreeNode) {
				
				TreeNode tn = (TreeNode) object;
				
				for(Action action: tn.getAllowedActions(viewer))
				{
					manager.add(action);
				}
				
			}
		}
		manager.add(new Separator());

	}

	/**
	 * Fill local pull down.
	 * 
	 * @param manager
	 *            the manager
	 */
	private void fillLocalPullDown(IMenuManager manager) {

		manager.add(new Separator());

	}

	/**
	 * Fill local tool bar.
	 * 
	 * @param manager
	 *            the manager
	 */
	private void fillLocalToolBar(IToolBarManager manager) {

		manager.add(refreshAction);
		manager.add(loadAction);
		manager.add(new Separator());
		drillDownAdapter.addNavigationActions(manager);
	}

	/**
	 * Hook context menu.
	 */
	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				ObjectListView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}

	/**
	 * Hook double click action.
	 */
	private void hookDoubleClickAction() {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				manageBOAction.run();
			}
		});
	}

	/**
	 * Make actions.
	 */
	private void makeActions() {

		loadAction = new LoadProjectAction(viewer, null);

		refreshAction=new RefreshAction(viewer);
		
		editDescriptionAction = new EditDescriptionAction(viewer);		
		
		manageBOAction = new ManageBOAction(viewer);
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}

}