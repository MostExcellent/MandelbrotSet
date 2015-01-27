import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class MandelbrotSet extends PApplet {

double centerX;
double centerY;
int currentZoom;
boolean canClick;
public void setup()
{
  canClick = false;
  centerX = 0;
  centerY = 0;
  currentZoom = 1;
  noLoop();
  size(1000,500);
  redraw();
  stroke(255);
  point(width/2,height/2);
}
public void draw()
{
  canClick = false;
  background(0);
  mandelbrot(centerX,centerY,currentZoom);
  canClick = true;
}
public void mousePressed()
{
  if(canClick)
  {
    double w = width/2;
    double h = height/2;
    double clickXM = ((mouseX - w)/w)/currentZoom;
    double clickYM = -((mouseY - h)/h)/currentZoom;
    centerX += clickXM;
    centerY += clickYM;
    currentZoom *= 2;
    redraw();
  }
}
public void mandelbrot(double xCoord, double yCoord, double zoom)
{
  double cX;
  double cY;
  double x;
  double y;
  double xtemp;
  int iteration;
  double maxiter = zoom*50;
  int t;
  int cT;
  double iC;
  double iCpct;
  double mu;
  double xFocus = (xCoord + (1.75f/zoom));
  double yFocus = -(yCoord + (-1/zoom));
  float pIter = 0;
  for(int i = 0; i < height; i++)
  {
   for(int n = 0; n < width; n++)
   {
     cX = map(n, 0, width, -3.5f/(float)zoom, 0) + xFocus;
     cY = map(i, 0, height, -2/(float)zoom, 0) + yFocus;
     x = 0;
     y = 0;
     for(iteration = 0;x*x + y*y < 2*2 && iteration <= maxiter; iteration++)
     {
       xtemp = x*x - y*y + cX;
       y = 2*x*y + cY;
       x = xtemp;
       iteration++;
     }
     pIter++;
     mu = (double)iteration - (Math.log(Math.log(Math.sqrt(x*x+y*y))) / Math.log(2));
     iCpct = (mu/maxiter);
     iC = iCpct*iCpct*255;
     if(mu == Double.NaN)
     {
       iC = 0;
     }
     stroke(0,0,(float)iC);
     point(n,i);
     System.out.println((pIter/(height*width))*100 + " % done");
   }
  }
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--full-screen", "--bgcolor=#666666", "--stop-color=#cccccc", "MandelbrotSet" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
