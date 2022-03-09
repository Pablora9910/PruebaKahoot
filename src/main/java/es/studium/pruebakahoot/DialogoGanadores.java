package es.studium.pruebakahoot;

import java.awt.BorderLayout;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import java.awt.Panel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.UIManager;
import java.awt.SystemColor;
import javax.swing.ImageIcon;

public class DialogoGanadores extends JDialog {

	
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	
	public DialogoGanadores() {
		setBackground(SystemColor.activeCaption);
		setTitle("GAME OVER\r\n");
		setIconImage(Toolkit.getDefaultToolkit().getImage("examen.png"));
		setBounds(100, 100, 413, 154);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(SystemColor.activeCaption);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			Panel panel = new Panel();
			panel.setBackground(UIManager.getColor("menu"));
			panel.setBounds(73, 10, 279, 57);
			contentPanel.add(panel);
			panel.setLayout(null);
			
			JLabel lblNewLabel_1 = new JLabel("HAS GANADO \r\n");
			lblNewLabel_1.setBounds(89, 34, 202, 13);
			panel.add(lblNewLabel_1);
			{
				JLabel lblNewLabel = new JLabel("ENHORABUENA\r\n");
				lblNewLabel.setBounds(79, 7, 214, 17);
				lblNewLabel.setBackground(UIManager.getColor("textInactiveText"));
				lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
				panel.add(lblNewLabel);
			}
			
			JPanel panel_1 = new JPanel();
			panel_1.setBounds(10, 5, 59, 52);
			panel.add(panel_1);
			panel_1.setLayout(null);
			
			JLabel lblNewLabel_2 = new JLabel("");
			lblNewLabel_2.setIcon(new ImageIcon("trofeo-de-campeonato.png"));
			lblNewLabel_2.setBounds(10, 0, 36, 42);
			panel_1.add(lblNewLabel_2);
		}
		setVisible(true);
	}
	
}