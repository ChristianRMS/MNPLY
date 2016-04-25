//package util;
//
//import org.eclipse.swt.widgets.Composite;
//import org.eclipse.swt.widgets.Button;
//import org.eclipse.swt.SWT;
//import org.eclipse.swt.events.MouseAdapter;
//import org.eclipse.swt.events.MouseEvent;
//
//public class TestGUI extends Composite {
//
//	/**
//	 * Create the composite.
//	 * @param parent
//	 * @param style
//	 */
//	public TestGUI(Composite parent, int style) {
//		super(parent, style);
//		
//		Button btnNewButton = new Button(this, SWT.NONE);
//		btnNewButton.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseDown(MouseEvent e) {
//				System.out.println("test");
//			}
//		});
//		btnNewButton.setBounds(10, 48, 83, 29);
//		btnNewButton.setText("New Button");
//
//	}
//
//	@Override
//	protected void checkSubclass() {
//		// Disable the check that prevents subclassing of SWT components
//	}
//
//}
