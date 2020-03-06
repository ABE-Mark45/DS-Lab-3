package eg.edu.alexu.csd.datastructure.iceHockey.cs5.Classes;

import eg.edu.alexu.csd.datastructure.iceHockey.cs5.Interfaces.IPlayersFinder;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class Solver implements IPlayersFinder
{
	
	Boolean[][] visited;
	int minx, miny, maxx, maxy, x, y;
	
	Boolean isValid(int i, int j, String[] photo, int team)
	{
		return (i >= 0 && i < y) && (j >= 0 && j < x) && visited[i][j] == false && (photo[i].charAt(j)-'0') == team;
	}
	
	
	int ff(int i, int j, String[] photo, int team)
	{
		visited[i][j] = true;
		int area = 1;
		
		minx = Math.min(minx, j);
		miny = Math.min(miny, i);
		maxx = Math.max(maxx, j);
		maxy = Math.max(maxy, i);
		
		
		if(isValid(i+1, j, photo, team))
			area += ff(i+1, j, photo, team);
		
		if(isValid(i-1, j, photo, team))
			area += ff(i-1, j, photo, team);
		
		if(isValid(i, j+1, photo, team))
			area += ff(i, j+1, photo, team);
		
		if(isValid(i, j-1, photo, team))
			area += ff(i, j-1, photo, team);

		return area;
	}
	
	void init_dims()
	{
		minx = 100000;
		miny = 100000;
		maxx = -5;
		maxy = -5;
	}
	
	public Point[] findPlayers(String[] photo, int team, int threshold)
	{
		
		
		if(photo == null || photo.length == 0)
			return null;
	
		y = photo.length;
		x = photo[0].length();
		
		visited = new Boolean[y][x];
		for(int i = 0; i < y;i++)
			for(int j = 0; j < x;j++)
				visited[i][j] = false;
		
		ArrayList<Point> points = new ArrayList<Point>();
		
		for(int i = 0; i < y; i++)
		{
			int area = 0;
			for(int j = 0; j < x;j++)
			{
				if(!visited[i][j] && (photo[i].charAt(j) - '0') == team)
				{
					init_dims();
					area = ff(i, j, photo, team);
					
					if(area * 4 >= threshold)
						points.add(new Point(minx + maxx + 1, miny + maxy + 1));
				}
			}
		}
		
		
		Collections.sort(points, new Comparator<Point>() {
			public int compare(Point a, Point b)
			{
				if(a.x < b.x)
					return -1;
				else if(a.x > b.x)
					return 1;
				return a.y < b.y? -1: 1;
			}
		});
		
		
		return points.toArray(new Point[points.size()]);
	}
	
	public String showPoints(Point[] p)
	{
		if(p == null || p.length == 0)
			return "";
		
		StringBuilder sbr = new StringBuilder();
		sbr.append("{ ");
		int n = p.length;
		for(int i = 0; i < n-1;i++)
		{
			sbr.append("(" + (int)p[i].getX() + ", " + (int)p[i].getY() + "), ");
		}
		sbr.append("(" + (int)p[n-1].getX() + ", " + (int)p[n-1].getY() + ") }");
		return sbr.toString();
	}
	
	public static void main(String[] args) {
		Solver s = new Solver();
		Point[] p = s.findPlayers(new String[] {
				"33JUBU33",
				"3U3O4433",
				"O33P44NB",
				"PO3NSDP3",
				"VNDSD333",
				"OINFD33X"
		}, 3, 16);
		
		s.showPoints(p);
	}
	
}

