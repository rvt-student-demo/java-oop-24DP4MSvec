package rvt.Todolistik;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class UserInterface {

    private final DefaultListModel<String> listModel = new DefaultListModel<>();
    private final TodoList todoList = new TodoList();
    private JLabel statusLabel;
    private JList<String> taskList;

    public void start() {
        SwingUtilities.invokeLater(this::createAndShowGui);
    }

    private void createAndShowGui() {
        JFrame frame = new JFrame("Todo List");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLocationRelativeTo(null);

        JPanel contentPane = new JPanel(new BorderLayout(10, 10));
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        taskList = new JList<>(listModel);
        taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(taskList);

        JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
        JTextField taskField = new JTextField();
        JButton addButton = new JButton("Add");
        inputPanel.add(taskField, BorderLayout.CENTER);
        inputPanel.add(addButton, BorderLayout.EAST);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton removeButton = new JButton("Remove Selected");
        JButton refreshButton = new JButton("Refresh List");
        buttonPanel.add(removeButton);
        buttonPanel.add(refreshButton);

        statusLabel = new JLabel("Ready.");
        statusLabel.setForeground(Color.DARK_GRAY);

        JPanel bottomPanel = new JPanel(new BorderLayout(5, 5));
        bottomPanel.add(buttonPanel, BorderLayout.CENTER);
        bottomPanel.add(statusLabel, BorderLayout.SOUTH);

        contentPane.add(scrollPane, BorderLayout.CENTER);
        contentPane.add(inputPanel, BorderLayout.NORTH);
        contentPane.add(bottomPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> addTask(taskField));
        removeButton.addActionListener(e -> removeSelectedTask());
        refreshButton.addActionListener(e -> refreshTaskList());

        frame.setContentPane(contentPane);
        frame.setVisible(true);

        refreshTaskList();
    }

    private void addTask(JTextField taskField) {
        String task = taskField.getText().trim();
        if (task.isEmpty()) {
            setStatus("Please enter a task.");
            return;
        }

        if (!todoList.checkEventStrings(task)) {
            setStatus("Error: Task must be at least 3 characters and contain only letters, digits, spaces, or Latvian characters.");
            return;
        }

        todoList.add(task);
        taskField.setText("");
        refreshTaskList();
        setStatus("Task added.");
    }

    private void removeSelectedTask() {
        String selectedValue = taskList.getSelectedValue();
        if (selectedValue == null) {
            setStatus("Please select a task to remove.");
            return;
        }

        int separator = selectedValue.indexOf(". ");
        if (separator < 0) {
            setStatus("Unable to parse the selected task.");
            return;
        }

        try {
            int id = Integer.parseInt(selectedValue.substring(0, separator));
            boolean removed = todoList.removeById(id);
            if (removed) {
                refreshTaskList();
                setStatus("Task removed.");
            } else {
                setStatus("Could not remove the selected task.");
            }
        } catch (NumberFormatException e) {
            setStatus("Unable to parse the selected task id.");
        }
    }

    private void refreshTaskList() {
        List<TodoItem> tasks = todoList.getTasks();
        listModel.clear();
        for (TodoItem item : tasks) {
            listModel.addElement(item.toString());
        }
    }

    private void setStatus(String message) {
        statusLabel.setText(message);
    }
}
