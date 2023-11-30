package ch.hearc.concurrence.animation.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainFrame extends JFrame {

	private JPanel pnlMain;

	private Box boxBottom;

	private JButton btnClear;
	private JCheckBox cbxRandomAngle;

	private JLabel lblInformations;

	private PaintingPanel pnlPainting;

	public MainFrame() {

		setTitle("Animation concurrence");

		setDefaultCloseOperation(EXIT_ON_CLOSE);

		setSize(800, 600);

		setResizable(false);

		setLocationRelativeTo(null);

		initComponents();

		addListeners();

	}

	private void initComponents() {
		pnlMain = new JPanel(new BorderLayout());
		pnlPainting = new PaintingPanel(this);
		lblInformations = new JLabel();

		boxBottom = Box.createHorizontalBox();

		btnClear = new JButton("Clear (c)");
		cbxRandomAngle = new JCheckBox("Random angle (r)");

		btnClear.setAlignmentX(CENTER_ALIGNMENT);
		cbxRandomAngle.setAlignmentX(CENTER_ALIGNMENT);
		cbxRandomAngle.setSelected(true);

		boxBottom.add(btnClear);
		boxBottom.add(Box.createHorizontalStrut(25));
		boxBottom.add(cbxRandomAngle);
		boxBottom.add(Box.createHorizontalGlue());
		boxBottom.add(lblInformations);
		boxBottom.add(Box.createHorizontalStrut(10));

		pnlMain.add(boxBottom, BorderLayout.SOUTH);
		pnlMain.add(pnlPainting, BorderLayout.CENTER);

		setContentPane(pnlMain);
	}

	private void addListeners() {

		btnClear.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				pnlPainting.clearPainting();
			}
		});

		pnlPainting.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				switch (e.getKeyChar()) {
				case 'c':
					btnClear.doClick();
					break;

				case 'r':
					cbxRandomAngle.doClick();
					break;
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		});

		cbxRandomAngle.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				pnlPainting.setIsRandomAngle(cbxRandomAngle.isSelected());
			}
		});
	}

	public void refreshInfo() {
		int nbElements=pnlPainting.getElementsNumber();
		lblInformations.setText(nbElements+" element"+((nbElements>1)?"s":""));
	}

}
