package dev.tdminhnhat.gui;

import dev.tdminhnhat.entity.DatabaseInformation;
import dev.tdminhnhat.enums.TypeDatabase;
import dev.tdminhnhat.service.GenerateDatabaseService;
import dev.tdminhnhat.service.TopicService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

class UserControlPanel extends JPanel implements ActionListener {

    private static Logger log = Logger.getLogger(UserControlPanel.class.getName());
    private final JComboBox<TypeDatabase> cbChooseTypeDatabase = new JComboBox<>(TypeDatabase.values());
    private final JComboBox<String> cbChooseTypeSource = new JComboBox<>(new String[]{"Default", "Users"});
    private final JComboBox<String> cbChooseUser = new JComboBox<>(TopicService.getListUsers());
    private final JComboBox<String> cbChooseTopic = new JComboBox<>(TopicService.getListDefaultTopics());
    private final JTextField txtInputHost = new JTextField("localhost");
    private final JTextField txtInputPort = new JTextField("1433");
    private final JTextField txtInputDatabaseName = new JTextField("");
    private final JTextField txtInputUsername = new JTextField("sa");
    private final JPasswordField pwdInputPassword = new JPasswordField("123456789");
    private final JButton btnGenerate = new JButton("Generate Database");
    private final JButton btnTestConnect = new JButton("Test Connect");
    private final JButton btnClearInput = new JButton("Clear Input");
    private final JButton btnExportClass = new JButton("Export Class");

    public UserControlPanel() {
        this.setLayout(new BorderLayout());
        Box box = new Box(BoxLayout.Y_AXIS);

        // Row 1: Title of Panel
        JPanel row1 = new JPanel();
        row1.setLayout(new BoxLayout(row1, BoxLayout.X_AXIS));
        JLabel title = new JLabel("Input Database Connection Information", JLabel.CENTER);
        title.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        row1.add(title);

        // Row 2: Input host, port and choose type database
        JPanel row2 = new JPanel();
        row2.setLayout(new BoxLayout(row2, BoxLayout.X_AXIS));
        JLabel lbInputHost = new JLabel("Host:");
        lbInputHost.setFont(new Font("", Font.PLAIN, 15));
        lbInputHost.setPreferredSize(new Dimension(50, 30));
        row2.add(lbInputHost);
        row2.add(txtInputHost);
        JLabel lbInputPort = new JLabel("Port:");
        lbInputPort.setFont(new Font("", Font.PLAIN, 15));
        lbInputPort.setPreferredSize(new Dimension(50, 30));
        row2.add(lbInputPort);
        row2.add(txtInputPort);
        JLabel lbChooseTypeDatabase = new JLabel("Type Database:");
        lbChooseTypeDatabase.setFont(new Font("", Font.PLAIN, 15));
        lbChooseTypeDatabase.setPreferredSize(new Dimension(150, 30));
        row2.add(lbChooseTypeDatabase);
        row2.add(cbChooseTypeDatabase);

        // Row 3: Input database name, username and password
        JPanel row3 = new JPanel();
        row3.setLayout(new BoxLayout(row3, BoxLayout.X_AXIS));
        JLabel lbInputDatabaseName = new JLabel("Database Name:");
        lbInputDatabaseName.setFont(new Font("", Font.PLAIN, 15));
        lbInputDatabaseName.setPreferredSize(new Dimension(150, 30));
        row3.add(lbInputDatabaseName);
        row3.add(txtInputDatabaseName);
        JLabel lbInputUsername = new JLabel("Username:");
        lbInputUsername.setFont(new Font("", Font.PLAIN, 15));
        lbInputUsername.setPreferredSize(new Dimension(100, 30));
        row3.add(lbInputUsername);
        row3.add(txtInputUsername);
        JLabel lbInputPassword = new JLabel("Password:");
        lbInputPassword.setFont(new Font("", Font.PLAIN, 15));
        lbInputPassword.setPreferredSize(new Dimension(100, 30));
        row3.add(lbInputPassword);
        row3.add(pwdInputPassword);

        // Row 4: Choose the topics to generate database
        JPanel row4 = new JPanel();
        row4.setLayout(new BoxLayout(row4, BoxLayout.X_AXIS));
        JLabel lbChooseTypeSource = new JLabel("Type Source:");
        lbChooseTypeSource.setFont(new Font("", Font.PLAIN, 15));
        lbChooseTypeSource.setPreferredSize(new Dimension(150, 30));
        row4.add(lbChooseTypeSource);
        row4.add(cbChooseTypeSource);
        JLabel lbChooseUser = new JLabel("User:");
        lbChooseUser.setFont(new Font("", Font.PLAIN, 15));
        lbChooseUser.setPreferredSize(new Dimension(100, 30));
        row4.add(lbChooseUser);
        row4.add(cbChooseUser);
        JLabel lbChooseTopic = new JLabel("Topic:");
        lbChooseTopic.setFont(new Font("", Font.PLAIN, 15));
        lbChooseTopic.setPreferredSize(new Dimension(100, 30));
        row4.add(lbChooseTopic);
        row4.add(cbChooseTopic);

        // Row 5: Button Generate, Test Connect, Clear Input and Export Class
        JPanel row5 = new JPanel();
        row5.setLayout(new BoxLayout(row5, BoxLayout.X_AXIS));
        row5.add(btnGenerate);
        row5.add(Box.createRigidArea(new Dimension(10, 0)));
        row5.add(btnTestConnect);
        row5.add(Box.createRigidArea(new Dimension(10, 0)));
        row5.add(btnClearInput);
        row5.add(Box.createRigidArea(new Dimension(10, 0)));
        row5.add(btnExportClass);

        box.add(row1);
        box.add(row2);
        box.add(row3);
        box.add(row4);
        box.add(row5);

        addEvent();
        defaultSetting();
        this.add(box, BorderLayout.CENTER);
    }

    private void addEvent() {
        cbChooseTypeDatabase.addActionListener(e -> {
            txtInputPort.setText(((TypeDatabase) cbChooseTypeDatabase.getSelectedItem()).getPort().toString());
        });
        cbChooseTypeSource.addActionListener((e) -> {
            if (Objects.equals(cbChooseTypeSource.getSelectedItem().toString(), "Default")) {
                cbChooseUser.setEnabled(false);
                DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>(
                        TopicService.getListDefaultTopics()
                );
                cbChooseTopic.setModel(comboBoxModel);
                ListEntitiesPanel.addDataToTable(null, cbChooseTopic.getSelectedItem().toString());
            } else {
                cbChooseUser.setEnabled(true);
                DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>(
                        TopicService.getListTopicsByUser(cbChooseUser.getSelectedItem().toString())
                );
                cbChooseTopic.setModel(comboBoxModel);
                ListEntitiesPanel.addDataToTable(cbChooseUser.getSelectedItem().toString(), cbChooseTopic.getSelectedItem().toString());
            }
            this.repaint();
        });
        cbChooseTopic.addActionListener((e) -> {
            ListEntitiesPanel.addDataToTable(
                    cbChooseUser.isEnabled() ? cbChooseUser.getSelectedItem().toString() : null, cbChooseTopic.getSelectedItem().toString());
        });
        btnGenerate.addActionListener(this);
        btnTestConnect.addActionListener(this);
        btnClearInput.addActionListener(this);
        btnExportClass.addActionListener(this);
    }

    private void defaultSetting() {
        this.cbChooseUser.setEnabled(false);
        ListEntitiesPanel.addDataToTable(null, cbChooseTopic.getSelectedItem().toString());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Generate Database" -> {
                if (checkInputEmpty()) {
                    toggleActionButton(false);
                    new Thread(() -> {
                        try {
                            DatabaseInformation databaseInformation = new DatabaseInformation(
                                    txtInputHost.getText(),
                                    Integer.parseInt(txtInputPort.getText()),
                                    txtInputUsername.getText(),
                                    String.valueOf(pwdInputPassword.getPassword()),
                                    txtInputDatabaseName.getText(),
                                    ((TypeDatabase) cbChooseTypeDatabase.getSelectedItem()),
                                    null
                            );
                            GenerateDatabaseService.generateDatabase(databaseInformation, TopicService.getListClassNatureTopic(
                                    cbChooseUser.isEnabled() ? cbChooseUser.getSelectedItem().toString() : null,
                                    cbChooseTopic.getSelectedItem().toString()
                            ));
                            JOptionPane.showMessageDialog(null, "Generate database successfully!", "Generate Database Info", JOptionPane.INFORMATION_MESSAGE);
                        } catch (Exception ex) {
                            log.log(Level.WARNING, ex.getMessage());
                            JOptionPane.showMessageDialog(null, "There are something wrong when executing generate database. Read the log and try again", "Generate Database Error", JOptionPane.ERROR_MESSAGE);
                        } finally {
                            toggleActionButton(true);
                        }
                    }).start();
                }
            }
            case "Test Connect" -> {
                if (checkInputEmpty()) {
                    toggleActionButton(false);
                    new Thread(() -> {
                        DatabaseInformation databaseInformation = new DatabaseInformation(
                                txtInputHost.getText(),
                                Integer.parseInt(txtInputPort.getText()),
                                txtInputUsername.getText(),
                                String.valueOf(pwdInputPassword.getPassword()),
                                txtInputDatabaseName.getText(),
                                ((TypeDatabase) cbChooseTypeDatabase.getSelectedItem()),
                                null
                        );
                        boolean result = new GenerateDatabaseService().testConnection(databaseInformation);
                        if (result) {
                            JOptionPane.showMessageDialog(null, "Connect to database successfully!", "Connection Success", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "Connect to database failed!", "Connection Failed", JOptionPane.ERROR_MESSAGE);
                        }
                        toggleActionButton(true);
                    }).start();
                }
            }
            case "Clear Input" -> {
                txtInputHost.setText("localhost");
                txtInputPort.setText("1433");
                txtInputDatabaseName.setText("");
                txtInputUsername.setText("sa");
                pwdInputPassword.setText("123456789");
                cbChooseTypeDatabase.setSelectedIndex(0);
                txtInputHost.setFocusable(true);
            }
            case "Export Class" -> {

            }
        }

    }

    private void toggleActionButton(boolean value) {
        btnTestConnect.setEnabled(value);
        btnClearInput.setEnabled(value);
        btnGenerate.setEnabled(value);
        btnExportClass.setEnabled(value);
    }

    private void showMessageError(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private boolean checkInputEmpty() {
        if (txtInputHost.getText().isEmpty()) {
            showMessageError("Please enter a valid Host");
            return false;
        } else if (txtInputPort.getText().isEmpty()) {
            showMessageError("Please enter a valid Port");
            return false;
        } else if (txtInputUsername.getText().isEmpty()) {
            showMessageError("Please enter a valid Username");
            return false;
        } else if (pwdInputPassword.getPassword().length == 0) {
            showMessageError("Please enter a valid Password");
            return false;
        } else if (txtInputDatabaseName.getText().isEmpty()) {
            showMessageError("Please enter a valid Database Name");
            return false;
        }
        return true;
    }
}
