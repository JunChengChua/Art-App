package Art;

import javax.swing.JFrame;

import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.JSlider;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.MatteBorder;
import javax.swing.JCheckBox;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import javax.swing.JComponent;

public class Artist extends JFrame 
{
	
	JFrame frame = new JFrame();
	private String stuff;
	private ArrayList<Point> points = new ArrayList<Point>();
	private ArrayList<ArrayList<Point>> pointsList = new ArrayList<ArrayList<Point>>();
	private ArrayList<Float> pointsThickness = new ArrayList<Float>();
	private int currentPoint = 0;

	//private ArrayList<Rectangle> rectangles = new ArrayList<Rectangle>();
	
	public Artist()
	{
		
		setTitle("Artist");
		//GridBagLayout layout = new GridBagLayout();
		setLayout(new BorderLayout());
		setLocation(100,100);
		setSize(800,650);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		Dimension sideDemens = new Dimension(200,0);
		
		JPanel topMenu = new JPanel();
		topMenu.setPreferredSize(new Dimension(1,50));
		topMenu.setBorder(new BevelBorder(BevelBorder.RAISED));
		add(topMenu, BorderLayout.NORTH);
		
		JPanel rightBorder = new JPanel();
		rightBorder.setPreferredSize(sideDemens);
		add(rightBorder, BorderLayout.EAST);
		
		JButton undo = new JButton("Undo");
		undo.addActionListener(new ActionListener()
				{

					public void actionPerformed(ActionEvent e) {
						
						pointsList.remove(pointsList.size() - 1);
						repaint();
					}
			
				});
		rightBorder.add(undo);
		
		final JPanel leftBorder = new JPanel();
		leftBorder.setPreferredSize(sideDemens);
		leftBorder.setLayout(new FlowLayout());
		add(leftBorder, BorderLayout.WEST);
		
		final JTextArea testText = new JTextArea();
		
		final JCheckBox incbySpeed = new JCheckBox("Thickness by Speed");
		incbySpeed.setMaximumSize(new Dimension(200, incbySpeed.getPreferredSize().height));
		leftBorder.add(incbySpeed);
		
		final JSlider markerSize = new JSlider(SwingConstants.VERTICAL, 1, 10, 1);
		leftBorder.add(markerSize);
		markerSize.addMouseMotionListener(new MouseMotionListener()
				{

					public void mouseDragged(MouseEvent e) {
						testText.setText("Size: " + markerSize.getValue());
						leftBorder.add(testText);
					}

					public void mouseMoved(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
				});

		
		frame.pack();
		final JPanel drawingArea = new JPanel() {
			protected void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D)g;
				g2d.setColor(Color.BLUE);
				// We can save this for the rectangles drawing button
				/* for (Rectangle rectangle : rectangles)
				{
					g.fillRect(rectangle.x,rectangle.y, rectangle.width, rectangle.height);
				} */

				//Spline Interpolation for when the mouse is moving too fast
				try
				{
					if (points.size() >= 2)
					{
						for (int j = 1; j < points.size();j++)
						{
							Point p1 = points.get(j - 1);
							Point p2 = points.get(j);
							if (incbySpeed.isSelected())
							{
								g2d.setStroke(new BasicStroke((float)(markerSize.getValue() + p1.distance(p2))));
							}
							else
							{
								g2d.setStroke(new BasicStroke(markerSize.getValue()));
							}

							g2d.drawLine(p1.x,  p1.y,  p2.x,  p2.y);
							System.out.println(p1 + " " + p2);
						}
					}
					for (int i = 0; i < pointsList.size();i++)
					{
						if (pointsList.get(i).size() >= 2) {
		                    List<Point> points = pointsList.get(i);
		                    for (int j = 1; j < points.size(); j++) {
		                    	Point p1 = points.get(j - 1);
		                        Point p2 = points.get(j);
		                        if (incbySpeed.isSelected())
								{
									g2d.setStroke(new BasicStroke((float)(pointsThickness.get(i) + p1.distance(p2))));
								}
		                        else
		                        {
		                        	g2d.setStroke(new BasicStroke(pointsThickness.get(i)));
		                        }
		                        
		                        g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
		                        System.out.println(p1 + " " + p2);
		                    }
		                }
						

					}
				}
				catch (Exception e)
				{
					
				}
				
			}
		};
		drawingArea.setBorder(new MatteBorder(2,2,2,2,Color.BLACK));
		drawingArea.addMouseListener(new MouseListener()
				{

					public void mouseClicked(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}

					public void mousePressed(MouseEvent e) {
						// TODO Auto-generated method stub
						pointsThickness.add((float)markerSize.getValue());
						points.add(e.getPoint());
					}

					public void mouseReleased(MouseEvent e) {
						pointsList.add(points);
						points = new ArrayList<Point>();
						System.out.println(pointsList.get(currentPoint));
						currentPoint++;
							
					}

					public void mouseEntered(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}

					public void mouseExited(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
				});
		drawingArea.addMouseMotionListener(new MouseMotionListener()
		{
			public void mouseDragged(MouseEvent e) {
				
				//Rectangle Section
				/*int x = e.getX();
				int y = e.getY();
				
				Rectangle currentRectangle = new Rectangle(x,y,10,10);
				
				rectangles.add(currentRectangle);
				
				drawingArea.repaint(x,y,10,10);
				*/
				
				points.add(e.getPoint());
				
				drawingArea.repaint();
			}

			public void mouseMoved(MouseEvent e) {
		
			}
		});
		add(drawingArea, BorderLayout.CENTER);
		
		setVisible(true);
	}
	
    private List<Point> generateSmoothedPoints() {
        List<Point> smoothedPoints = new ArrayList<Point>();

        // Perform spline interpolation or other interpolation technique here
        // to generate additional points between capturedPoints.

        for (int i = 1; i < points.size(); i++) {
        	if (points.get(i).getY() == (double)-1)
        	{
        		smoothedPoints.add(new Point(-1,-1));
        		i += 2;
        	}
        	else
        	{
        		
        		Point p1 = points.get(i - 1);
                Point p2 = points.get(i);
                smoothedPoints.add(p1);

                int steps = Math.max(Math.abs(p2.x - p1.x), Math.abs(p2.y - p1.y));
                for (int j = 1; j <= steps; j++) {
                    int x = p1.x + (p2.x - p1.x) * j / steps;
                    int y = p1.y + (p2.y - p1.y) * j / steps;
                    smoothedPoints.add(new Point(x, y));
                }
        	}
        }

        return smoothedPoints;
    }

	
	public static void main(String[] args)
	{
		Artist art = new Artist();
	}
}
