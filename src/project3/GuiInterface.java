/*
Name: Fahim Zubaer
title: Two-Tier Client-Server Application Development With MySQL and JDBC
*/

package project3;


import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class GuiInterface {
		
	private static boolean connectedToDatabase = false;
	
	private static Connection connection;
	private static Statement statement;
	
	private static ResultSetTableModel tableModel;
	
	static String [] JDBC_Drivers  = new String[] { "com.mysql.jdbc.Driver" };
	static String [] Database_URLs = new String[] { "jdbc:mysql://localhost:3306/project3" };
	
	static JPanel Middle = new JPanel();
	static JPanel Bottom = new JPanel();
	static JPanel Container = new JPanel();
	static JPanel TopLeft = new JPanel();
	static JPanel TopRight = new JPanel();
		
	static JButton Button_Connect_to_Database = new JButton("Connect to Database");
	static JButton Button_Clear_Command = new JButton("Clear SQL Command");
	static JButton Button_Execute_SQL_Command = new JButton("Execute SQL Command");
	static JButton Button_Clear_Result_Window = new JButton("Clear Result Window");
		
	static JFrame windowFrame = new JFrame();
		
	static JScrollPane TextArea = new JScrollPane();
	static JScrollPane sqlResults = new JScrollPane();
	
	static JComboBox<String> JDBC_Driver_box  = new JComboBox<>(JDBC_Drivers);
	static JComboBox<String> Database_URL_box = new JComboBox<>(Database_URLs);
	
	static JTextField TextField_Username = new JTextField();
	static JPasswordField TextField_Password = new JPasswordField();
	static JTextArea TextArea_Commands = new JTextArea(20,30);
	
	static SpringLayout guiMiddleLayout = new SpringLayout();
	static SpringLayout guiBottomLayout = new SpringLayout();
	static SpringLayout guiContainerLayout = new SpringLayout();
	static SpringLayout guiTopLeftLayout = new SpringLayout();
	static SpringLayout guiTopRightLayout = new SpringLayout();
	
	static JLabel Title = new JLabel("Enter Database Information", SwingConstants.LEFT);
	static JLabel Blank = new JLabel("", SwingConstants.LEFT);
	static JLabel Username = new JLabel("Username", SwingConstants.LEFT);
	static JLabel Password = new JLabel("Password", SwingConstants.LEFT);
	static JLabel JDBC_Driver = new JLabel("JDBC Driver", SwingConstants.LEFT);
	static JLabel Database_URL= new JLabel("Database URL", SwingConstants.LEFT);
	static JLabel Label_Title = new JLabel("Enter a SQL Command", SwingConstants.LEFT);
	static JLabel Status = new JLabel("No Connection now", SwingConstants.LEFT);
	static JLabel ResultsLabel_Title = new JLabel("SQL Execution Result Window", SwingConstants.LEFT);
	
		


	public static void main(String[] args)
	{		
		setGui();
		setGuiMiddle();
		setGuiBottom();
		setGuiTopLeft();
		setGuiTopRight();

		attachEventListeners();
	}

	public static void setGui()
	{
				
		Container.add(Middle);
		Container.add(Bottom);
		Container.add(TopLeft);
		Container.add(TopRight);
		Container.setLayout(guiContainerLayout);
				
		guiContainerLayout.putConstraint(SpringLayout.NORTH, Middle, 10, SpringLayout.SOUTH, TopLeft);
		guiContainerLayout.putConstraint(SpringLayout.WEST, Middle, 5, SpringLayout.WEST, Container);
		
		guiContainerLayout.putConstraint(SpringLayout.NORTH, Bottom, 10, SpringLayout.SOUTH, Middle);
		guiContainerLayout.putConstraint(SpringLayout.WEST, Bottom, 5, SpringLayout.WEST, Container);
		
		guiContainerLayout.putConstraint(SpringLayout.WEST, TopLeft, 40, SpringLayout.NORTH, Container);
		guiContainerLayout.putConstraint(SpringLayout.NORTH, TopLeft, 5, SpringLayout.WEST, Container);
		
		guiContainerLayout.putConstraint(SpringLayout.WEST, TopRight, 10, SpringLayout.EAST, TopLeft);
		guiContainerLayout.putConstraint(SpringLayout.NORTH, TopRight, 5, SpringLayout.NORTH, Container);
				
		windowFrame.add(Container);		
		windowFrame.setTitle("SQL Client GUI - (FZ - Fall 2017)");
		windowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		windowFrame.setSize(850,700);
		windowFrame.setLocationRelativeTo(null);
		windowFrame.setVisible(true);
	}
	
	// Setting up the top left of the window
	public static void setGuiTopLeft()
	{
		TopLeft.setLayout(guiTopLeftLayout);
		TopLeft.setPreferredSize(new Dimension(390, 230));
		
		Title.setPreferredSize(new Dimension(390, 30));
		JDBC_Driver.setPreferredSize(new Dimension(90, 30));
		Database_URL.setPreferredSize(new Dimension(90, 30));
		Username.setPreferredSize(new Dimension(90, 30));
		Password.setPreferredSize(new Dimension(90, 30));
		TextField_Username.setPreferredSize(new Dimension(280, 30));
		TextField_Password.setPreferredSize(new Dimension(280, 30));
		JDBC_Driver_box.setPreferredSize(new Dimension(280, 30));
		Database_URL_box.setPreferredSize(new Dimension(280, 30));
		
		TopLeft.add(Username);
		TopLeft.add(Password);
		TopLeft.add(Database_URL);
		TopLeft.add(JDBC_Driver_box);
		TopLeft.add(Database_URL_box);
		TopLeft.add(TextField_Username);
		TopLeft.add(TextField_Password);
		TopLeft.add(Title);
		TopLeft.add(JDBC_Driver);
		
		guiTopLeftLayout.putConstraint(SpringLayout.NORTH, JDBC_Driver, 5,
				SpringLayout.SOUTH, Title);
		guiTopLeftLayout.putConstraint(SpringLayout.NORTH, Database_URL, 5,
				SpringLayout.SOUTH, JDBC_Driver);
		guiTopLeftLayout.putConstraint(SpringLayout.NORTH, Username, 5,
				SpringLayout.SOUTH, Database_URL);
		guiTopLeftLayout.putConstraint(SpringLayout.NORTH, Password, 5,
				SpringLayout.SOUTH, Username);
		
		guiTopLeftLayout.putConstraint(SpringLayout.WEST, JDBC_Driver_box, 10,
				SpringLayout.EAST, JDBC_Driver);
		guiTopLeftLayout.putConstraint(SpringLayout.WEST, Database_URL_box, 10,
				SpringLayout.EAST, Database_URL);
		guiTopLeftLayout.putConstraint(SpringLayout.WEST, TextField_Username, 10,
				SpringLayout.EAST, Username);
		guiTopLeftLayout.putConstraint(SpringLayout.WEST, TextField_Password, 10,
				SpringLayout.EAST, Password);

		guiTopLeftLayout.putConstraint(SpringLayout.NORTH, JDBC_Driver_box, 5,
				SpringLayout.SOUTH, Title);
		guiTopLeftLayout.putConstraint(SpringLayout.NORTH, Database_URL_box, 5,
				SpringLayout.SOUTH, JDBC_Driver_box);
		guiTopLeftLayout.putConstraint(SpringLayout.NORTH, TextField_Username, 5,
				SpringLayout.SOUTH, Database_URL_box);
		guiTopLeftLayout.putConstraint(SpringLayout.NORTH, TextField_Password, 5,
				SpringLayout.SOUTH, TextField_Username);
	}
	
	// Setting up the top right of the window
	public static void setGuiTopRight()
	{
		
		Label_Title.setPreferredSize(new Dimension(380, 25));
		TextArea.setPreferredSize(new Dimension(380, 190));
		
		TopRight.add(Label_Title);
		TopRight.add(TextArea);
		
		TopRight.setLayout(guiTopRightLayout);
		TopRight.setPreferredSize(new Dimension(390, 225));
	
		TextArea.getViewport().add(TextArea_Commands,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		guiTopRightLayout.putConstraint(SpringLayout.NORTH, TextArea, 5,
				SpringLayout.SOUTH, Label_Title);
	}
	
	// Setting up the middle part of the window
	public static void setGuiMiddle()
	{
		Middle.setPreferredSize(new Dimension(830, 70));
		Middle.setLayout(guiMiddleLayout);
		
		Button_Connect_to_Database.setPreferredSize(new Dimension(185, 30));
		Button_Clear_Command.setPreferredSize(new Dimension(152, 30));
		Button_Execute_SQL_Command.setPreferredSize(new Dimension(168, 30));
		Status.setPreferredSize(new Dimension(290, 25));
		
		Middle.add(Button_Connect_to_Database);
		Middle.add(Button_Clear_Command);
		Middle.add(Button_Execute_SQL_Command);
		Middle.add(Status);
				
		guiMiddleLayout.putConstraint(SpringLayout.NORTH, Button_Clear_Command, 5,
				SpringLayout.NORTH, Middle);
		guiMiddleLayout.putConstraint(SpringLayout.NORTH, Button_Execute_SQL_Command, 5,
				SpringLayout.NORTH, Middle);
		guiMiddleLayout.putConstraint(SpringLayout.NORTH, Status, 5,
				SpringLayout.NORTH, Middle);
		guiMiddleLayout.putConstraint(SpringLayout.NORTH, Button_Connect_to_Database, 5,
				SpringLayout.NORTH, Middle);
		
		guiMiddleLayout.putConstraint(SpringLayout.WEST, Button_Clear_Command, 5,
				SpringLayout.EAST, Button_Connect_to_Database);
		guiMiddleLayout.putConstraint(SpringLayout.WEST, Button_Execute_SQL_Command, 5,
				SpringLayout.EAST, Button_Clear_Command);
		guiMiddleLayout.putConstraint(SpringLayout.WEST, Status, 5,
				SpringLayout.WEST, Middle);
		guiMiddleLayout.putConstraint(SpringLayout.WEST, Button_Connect_to_Database, 5,
				SpringLayout.EAST, Status);

		Button_Connect_to_Database.setForeground(Color.WHITE);
		Status.setForeground(Color.RED);
		Button_Connect_to_Database.setBackground(Color.BLUE);
		Button_Clear_Command.setBackground(Color.WHITE);
		Button_Clear_Command.setForeground(Color.RED);
		Button_Execute_SQL_Command.setBackground(Color.GREEN);
		
		ResultsLabel_Title.setForeground(Color.BLUE);
		Label_Title.setForeground(Color.BLUE);
		Title.setForeground(Color.BLUE);
	}
	
	// Setting up the bottom part of the window
	public static void setGuiBottom()
	{
		Bottom.setPreferredSize(new Dimension(890, 335));
		Bottom.setLayout(guiBottomLayout);
		
		ResultsLabel_Title.setPreferredSize(new Dimension(800, 30));
		sqlResults.setPreferredSize(new Dimension(810, 260));
		Button_Clear_Result_Window.setPreferredSize(new Dimension(170, 30));
		
		Bottom.add(ResultsLabel_Title);
		Bottom.add(sqlResults);
		Bottom.add(Button_Clear_Result_Window);
		
		guiBottomLayout.putConstraint(SpringLayout.NORTH, Button_Clear_Result_Window, 5,
				SpringLayout.SOUTH, sqlResults);
		guiBottomLayout.putConstraint(SpringLayout.NORTH, sqlResults, 5,
				SpringLayout.SOUTH, ResultsLabel_Title);
		
		Button_Clear_Result_Window.setBackground(Color.YELLOW);
	}
		
	public static void attachEventListeners()
	{
	
		Button_Connect_to_Database.addActionListener( new ActionListener()
		{
			@SuppressWarnings("deprecation")
			public void actionPerformed( ActionEvent e )
			{
				if ( !isConnectedToDatabase() )
				{					
					if ( ( !TextField_Username.getText().isEmpty() ) &&
							( !TextField_Password.getText().isEmpty() ) )
					{
						try { connectToDatabase(); }
						catch (ClassNotFoundException e1) { e1.printStackTrace(); }
						catch (SQLException e1) { e1.printStackTrace(); }
					}
					
					else
						JOptionPane.showMessageDialog( null, "Please make sure to enter a username and password.",
								"Username and Password Required", JOptionPane.ERROR_MESSAGE );
				}
				
				else { disconnectFromDatabase(); }
			}
		});
			
		Button_Clear_Command.addActionListener( new ActionListener()
		{
			public void actionPerformed( ActionEvent e ) { TextArea_Commands.setText(""); }
		});
		
		
		Button_Execute_SQL_Command.addActionListener( new ActionListener()
		{
			public void actionPerformed( ActionEvent e )
			{
				executeQueryAndShowResults(
					(String)JDBC_Driver_box.getSelectedItem(),
					TextArea_Commands.getText()	
				);
			}
		});
				
		Button_Clear_Result_Window.addActionListener( new ActionListener()
		{
			public void actionPerformed( ActionEvent e ) { sqlResults.setViewportView(null); }
		});
	}
		
	@SuppressWarnings("deprecation")
	public static void connectToDatabase() throws SQLException, ClassNotFoundException
	{
		Class.forName( (String)JDBC_Driver_box.getSelectedItem() );
		
		connection = DriverManager.getConnection( (String)Database_URL_box.getSelectedItem(),
				TextField_Username.getText(), TextField_Password.getText() );
		
		setStatement(connection.createStatement( ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY ));
		
		connectedToDatabase = true;
		
		Button_Connect_to_Database.setBackground(Color.RED);
		Button_Connect_to_Database.setText("Disconnect from Database");
		Status.setText("Connected to " + (String)Database_URL_box.getSelectedItem());
	}
	
	
	public static void disconnectFromDatabase()
	{
		try
		{
			getStatement().close();
			connection.close();
		}
		catch ( SQLException sqlException )
		{
			sqlException.printStackTrace();
		}
		finally
		{
			connectedToDatabase = false;
			Button_Connect_to_Database.setBackground(Color.BLUE);
			Button_Connect_to_Database.setText("Connect to Database");
			Status.setText("No Connection Now");
		}
	}
	
	public static void executeQueryAndShowResults( String JDBC_DRIVER, String NEW_QUERY ) 
	{
		if ( isConnectedToDatabase() )
		{
			try 
			{
				tableModel = new ResultSetTableModel( JDBC_DRIVER, NEW_QUERY );
				JTable resultTable = new JTable( tableModel );
				sqlResults.getViewport().add( resultTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
						ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER );
			}
			catch ( ClassNotFoundException classNotFound ) { JOptionPane.showMessageDialog( null,
					"MySQL driver not found", "Driver not found", JOptionPane.ERROR_MESSAGE ); }
			catch ( SQLException sqlException ) { JOptionPane.showMessageDialog( null, sqlException.getMessage(),
					"Database error", JOptionPane.ERROR_MESSAGE ); }
		}
		else
		{
			JOptionPane.showMessageDialog( null, 
					"Not Connected to a Database\nPlease connect to a database before executing a command.",
					"No Database Connection", JOptionPane.ERROR_MESSAGE );
		}
	}

	
	public static boolean isConnectedToDatabase() { return connectedToDatabase; }
	public static Statement getStatement() { return statement; }
	public static void setStatement(Statement statement) { GuiInterface.statement = statement; }

}



