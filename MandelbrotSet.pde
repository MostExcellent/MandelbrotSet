double centerX;
double centerY;
int currentZoom;
boolean canClick;
void setup()
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
void draw()
{
  canClick = false;
  background(0);
  mandelbrot(centerX,centerY,currentZoom);
  canClick = true;
}
void mousePressed()
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
void mandelbrot(double xCoord, double yCoord, double zoom)
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
  int c1;
  int c2;
  double iCpct;
  float cFinal;
  double mu;
  double xFocus = (xCoord + (1.75/zoom));
  double yFocus = -(yCoord + (-1/zoom));
  float pIter = 0;
  for(int i = 0; i < height; i++)
  {
   for(int n = 0; n < width; n++)
   {
     cX = map(n, 0, width, -3.5/(float)zoom, 0) + xFocus;
     cY = map(i, 0, height, -2/(float)zoom, 0) + yFocus;
     x = 0;
     y = 0;
     for(iteration = 0;x*x + y*y < 2*2 && iteration < maxiter; iteration++)
     {
       xtemp = x*x - y*y + cX;
       y = 2*x*y + cY;
       x = xtemp;
     }
     pIter++;
     mu = (double)iteration - (Math.log(Math.log(Math.sqrt(x*x+y*y))) / Math.log(2))+1;
     iCpct = (mu/maxiter);
     iC = iCpct*254;
     c1 = (int)iC;
     c2 = c1 + 1;
     cFinal = linearInterpolate(c1,c2,(float)(mu % 1));
     if(iteration == maxiter)
     {
       cFinal = 0;
     }
     stroke(0,0,cFinal);
     point(n,i);
     System.out.println((pIter/(height*width))*100 + " % done");
   }
  }
}
float linearInterpolate(float a, float b, float f)
{
    return a + f * (b - a);
}
