package AccountView;

import javax.swing.*;
import DB.AccountManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class AccountView extends JFrame{
	private String year;
	private String month;
	private String day;
	public static JTable table;
	private AccountSettingView accountSettingView = null ; 
	public AccountView(final String year, final String month, final String day) {
		this.year = year;
		this.month = month;
		this.day = day;
        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        table.setRowHeight(30);
        table.setFont(new Font("Serif", Font.BOLD, 20));
        table.setEnabled(false);
        JLabel lblHeading = new JLabel("current day " + year + "/"+ month + "/" + day);
        lblHeading.setFont(new Font("Arial",Font.TRUETYPE_FONT,24));
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(lblHeading,BorderLayout.PAGE_START);
        getContentPane().add(scrollPane,BorderLayout.CENTER);
        setSize(800, 500);
        setVisible(true);

        JPanel panel=new JPanel();
        add(panel, BorderLayout.SOUTH);
        JButton addButton = new JButton("ADD CostRecord");
        JButton okButton = new JButton("EDIT/DELETE CostRecord");
        addButton.setFont(new Font("Arial", Font.PLAIN, 24));
        okButton.setFont(new Font("Arial", Font.PLAIN, 24));
        panel.add(addButton);
        panel.add(okButton);
        init();
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	if(accountSettingView == null || !accountSettingView.isDisplayable()) {
            		accountSettingView = new AccountSettingView(year,month,day);
                	accountSettingView.setModalExclusionType(getModalExclusionType());
                	accountSettingView.setVisible(true);
                	accountSettingView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            	}
            }
        });
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	AccountIDView accountIDView = new AccountIDView(year,month,day);
            	accountIDView.setModal(true);
            	accountIDView.setVisible(true);
            	accountIDView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            }
        });
	}

	public void init() {
		AccountManager test = new AccountManager(year,month,day);
        test.setCostRecord();
	}
}