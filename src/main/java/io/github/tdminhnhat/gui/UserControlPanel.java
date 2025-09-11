package io.github.tdminhnhat.gui;

import io.github.tdminhnhat.entity.DatabaseInformation;
import io.github.tdminhnhat.enums.TypeDatabase;
import io.github.tdminhnhat.service.GenerateDatabaseService;
import io.github.tdminhnhat.service.TopicService;

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
    private final JSpinner spInputPort = new JSpinner(new SpinnerNumberModel(1433, 0, 65536, 1));
    private final JTextField txtInputDatabaseName = new JTextField("");
    private final JTextField txtInputUsername = new JTextField("sa");
    private final JPasswordField pwdInputPassword = new JPasswordField("123456789");
    private final JButton btnGenerate = new JButton("Generate Database");
    private final JButton btnTestConnect = new JButton("Test Connect");
    private final JButton btnClearInput = new JButton("Clear Input");
    private final JButton btnExportClass = new JButton("Export Class");
    private final JButton btnViewDetail = new JButton("View Detail");

    public UserControlPanel() {
        Box box = new Box(BoxLayout.Y_AXIS);

        Box row1 = new Box(BoxLayout.X_AXIS);
        JLabel lbHost = new JLabel("Host:");
        lbHost.setPreferredSize(new Dimension(125, 30));
        txtInputHost.setPreferredSize(new Dimension(150, 30));
        row1.add(lbHost);
        row1.add(txtInputHost);
        box.add(row1);
        box.add(Box.createVerticalStrut(10));

        Box row2 = new Box(BoxLayout.X_AXIS);
        JLabel lbPort = new JLabel("Port:");
        lbPort.setPreferredSize(new Dimension(125, 30));
        spInputPort.setPreferredSize(new Dimension(150, 30));
        row2.add(lbPort);
        row2.add(spInputPort);
        box.add(row2);
        box.add(Box.createVerticalStrut(10));

        Box row3 = new Box(BoxLayout.X_AXIS);
        JLabel lbDatabaseName = new JLabel("Database Name:");
        lbDatabaseName.setPreferredSize(new Dimension(125, 30));
        txtInputDatabaseName.setPreferredSize(new Dimension(150, 30));
        row3.add(lbDatabaseName);
        row3.add(txtInputDatabaseName);
        box.add(row3);
        box.add(Box.createVerticalStrut(10));

        Box row4 = new Box(BoxLayout.X_AXIS);
        JLabel lbUsername = new JLabel("Username:");
        lbUsername.setPreferredSize(new Dimension(125, 30));
        txtInputUsername.setPreferredSize(new Dimension(150, 30));
        row4.add(lbUsername);
        row4.add(txtInputUsername);
        box.add(row4);
        box.add(Box.createVerticalStrut(10));

        Box row5 = new Box(BoxLayout.X_AXIS);
        JLabel lbPassword = new JLabel("Password:");
        lbPassword.setPreferredSize(new Dimension(125, 30));
        pwdInputPassword.setPreferredSize(new Dimension(150, 30));
        row5.add(lbPassword);
        row5.add(pwdInputPassword);
        box.add(row5);
        box.add(Box.createVerticalStrut(10));

        Box row6 = new Box(BoxLayout.X_AXIS);
        JLabel lbTypeDatabase = new JLabel("Type Database:");
        lbTypeDatabase.setPreferredSize(new Dimension(125, 30));
        cbChooseTypeDatabase.setPreferredSize(new Dimension(150, 30));
        row6.add(lbTypeDatabase);
        row6.add(cbChooseTypeDatabase);
        box.add(row6);
        box.add(Box.createVerticalStrut(10));

        Box row7 = new Box(BoxLayout.X_AXIS);
        JLabel lbTypeSource = new JLabel("Type Source:");
        lbTypeSource.setPreferredSize(new Dimension(125, 30));
        cbChooseTypeSource.setPreferredSize(new Dimension(150, 30));
        row7.add(lbTypeSource);
        row7.add(cbChooseTypeSource);
        box.add(row7);
        box.add(Box.createVerticalStrut(10));

        Box row8 = new Box(BoxLayout.X_AXIS);
        JLabel lbUsers = new JLabel("Choose User:");
        lbUsers.setPreferredSize(new Dimension(125, 30));
        cbChooseUser.setPreferredSize(new Dimension(150, 30));
        row8.add(lbUsers);
        row8.add(cbChooseUser);
        box.add(row8);
        box.add(Box.createVerticalStrut(10));

        Box row9 = new Box(BoxLayout.X_AXIS);
        JLabel lbTopics = new JLabel("Choose Topic:");
        lbTopics.setPreferredSize(new Dimension(125, 30));
        cbChooseTopic.setPreferredSize(new Dimension(150, 30));
        row9.add(lbTopics);
        row9.add(cbChooseTopic);
        box.add(row9);
        box.add(Box.createVerticalStrut(10));

        JPanel pnListActions = new JPanel();
        pnListActions.setLayout(new FlowLayout(FlowLayout.CENTER));
        pnListActions.add(btnGenerate);
        pnListActions.add(btnTestConnect);
        pnListActions.add(btnClearInput);
        pnListActions.add(btnExportClass);
        pnListActions.add(btnViewDetail);
        box.add(pnListActions);

        addEvent();
        defaultSetting();
        this.add(box);
    }

    private void addEvent() {
        cbChooseTypeDatabase.addActionListener(e -> {
            spInputPort.setValue(((TypeDatabase) cbChooseTypeDatabase.getSelectedItem()).getPort());
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
        cbChooseUser.addActionListener((e) -> {
            DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>(
                    TopicService.getListTopicsByUser(cbChooseUser.getSelectedItem().toString())
            );
            cbChooseTopic.setModel(comboBoxModel);
            ListEntitiesPanel.addDataToTable(cbChooseUser.getSelectedItem().toString(), cbChooseTopic.getSelectedItem().toString());
        });
        btnGenerate.addActionListener(this);
        btnTestConnect.addActionListener(this);
        btnClearInput.addActionListener(this);
        btnExportClass.addActionListener(this);
        btnViewDetail.addActionListener(this);
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
                                    Integer.parseInt(spInputPort.getValue().toString()),
                                    txtInputUsername.getText(),
                                    String.valueOf(pwdInputPassword.getPassword()),
                                    txtInputDatabaseName.getText(),
                                    ((TypeDatabase) cbChooseTypeDatabase.getSelectedItem()),
                                    null
                            );
                            GenerateDatabaseService.generateDatabase(databaseInformation, TopicService.getListClassWorkJPATopic(
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
                                Integer.parseInt(spInputPort.getValue().toString()),
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
                spInputPort.setValue(1433);
                txtInputDatabaseName.setText("");
                txtInputUsername.setText("sa");
                pwdInputPassword.setText("123456789");
                cbChooseTypeDatabase.setSelectedIndex(0);
                txtInputHost.setFocusable(true);
            }
            case "Export Class" -> {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fileChooser.showOpenDialog(null);
                String getPath = fileChooser.getSelectedFile().getPath();
                new Thread(() -> {
                    btnExportClass.setEnabled(false);
                    String result = GenerateDatabaseService.exportClass(getPath,
                            TopicService.getPackageNameSelectTopic(
                                    cbChooseUser.isEnabled() ? cbChooseUser.getSelectedItem().toString() : null,
                                    cbChooseTopic.getSelectedItem().toString()
                            ));
                    if(!Objects.isNull(result)) {
                        JOptionPane.showMessageDialog(null, result, "Export Classes", JOptionPane.ERROR_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Export classes successfully. Please check the folder. Remember changing your package name before use.", "Export Classes", JOptionPane.INFORMATION_MESSAGE);
                    }
                    btnExportClass.setEnabled(true);
                }).start();
            }
            case "View Detail" -> {
                new ShowDetailClassGUI();
            }
        }
    }

    private void toggleActionButton(boolean value) {
        btnTestConnect.setEnabled(value);
        btnClearInput.setEnabled(value);
        btnGenerate.setEnabled(value);
    }

    private void showMessageError(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private boolean checkInputEmpty() {
        if (txtInputHost.getText().isEmpty()) {
            showMessageError("Please enter a valid Host");
            return false;
        } else if (Objects.isNull(spInputPort.getValue())) {
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
