import java.util.Scanner;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Arrays;
import java.util.PriorityQueue;
public class graph{

    public static Scanner scn =new Scanner(System.in);
    static ArrayList<Edge>[] graph=new ArrayList[7];
    static ArrayList<Edge>[] dgraph=new ArrayList[7];
       // static ArrayList<ArrayList<Edge>> graph=new ArrayList<
    public static void main(String[] args)
    {
        boolean[] vis=new boolean[graph.length];
        caseI();
        //display();
       // bfs_shortestpath(0,6);
       // bfs_shortestpath_02(0,vis);
        int comp=0;
        for(int i=0;i<graph.length;i++)
        {
            if(!vis[i])
            {
         //       bfs_shortestpath_02(i,vis);
                comp++;
            }
        }
      //  System.out.println("disconnected components"+comp);
      kruskal();
    // prims();
       //Arrays.fill(dis,-1);
      //  root=0;
      //  AP(0,-1);
      //  points[0]=root>1?true:false;
       // for(int i=0;i<graph.length;i++){
      //      if(points[i])
      //      System.out.println(i);
     //   }
    }
    public static class Edge{
        int v=0;
        int w=0;
        Edge(int v,int w)
        {
            this.v=v;
            this.w=w;
        }
        Edge()
        {

        }
    }
    public static void caseI()
    {
        for(int i=0;i<graph.length;i++)
        {
        graph[i]=new ArrayList<Edge>();
        dgraph[i]=new ArrayList<Edge>();
        }
        addEdge(0,3,10);
        addEdge(0,1,10);
        addEdge(1,2,10);
        addEdge(2,3,40);
        addEdge(3,4,2);
        addEdge(4,5,2);
        addEdge(4,6,3);
        addEdge(5,6,8);
    }
    // public static void caseII()
    // {
    //     for(int i=0;i<7;i++){
    //       //  graph.add(new ArrayList<Edge>());
    //     }
    // }
    public static void addEdge(int u,int v,int w){
        if(u<0 || v<0 || u>=graph.length || v>=graph.length)
        return ;
        graph[u].add(new Edge(v,w));
        graph[v].add(new Edge(u,w));
    }
    public static void addEdge2(int u,int v,int w){
        if(u<0 || v< 0 || u>=dgraph.length || v>=dgraph.length)
        return;
        dgraph[u].add(new Edge(v,w));
        dgraph[v].add(new Edge(u,w));
    }
    public static void display()
    {
        for(int i=0;i<graph.length;i++)
        {
            System.out.print(i+" =>");
            for(int j=0;j<graph[i].size();j++)
            {
             System.out.print("("+graph[i].get(j).v+", "+graph[i].get(j).w + "), ");
            }
            System.out.println();
        }
    }
    public static void display2()
    {
        for(int i=0;i<dgraph.length;i++)
        {
            System.out.print(i + " =>" );
            for(int j=0;j<dgraph[i].size();j++)
            {
                System.out.print("("+dgraph[i].get(j).v + ", " + dgraph[i].get(j).w + "), ");
            }   
            System.out.println();
        }
    }
    public static class bfspair{ 
        int vtx=0;
        int wsf=0;
        String psf=" ";
        bfspair()
        {}
        bfspair(int vtx,int wsf,String psf)
        {
            this.vtx=vtx;
            this.wsf=wsf;
            this.psf=psf;
        }
    }
   public static  void bfs_shortestpath(int src,int des)
    {
        LinkedList<bfspair> que=new LinkedList<>();
        boolean[] vis=new boolean [graph.length];

        bfspair root=new bfspair(src,0,src+" ");

        que.addLast(root);
        que.addLast(null);

        int cyclecounter=0;
        boolean ispath=false;
        int level=1;

        while(que.size()!=1)
        {   //remove
            bfspair rpair=que.removeFirst();
            //cycle
            if(vis[rpair.vtx])
            {
             System.out.println("cycle number:"+cyclecounter+" : "+rpair.psf );
             cyclecounter++;
            }

            //mark
            vis[rpair.vtx]=true;

            //destination
            if(rpair.vtx==des && !ispath)
            {
                System.out.println(rpair.psf + " @ "+ rpair.wsf + "->" +level);
                ispath=true;
            }
            //nbr.
            for(Edge e:graph[rpair.vtx])
            {
                if(!vis[e.v])   //unmark nbr
               {
                bfspair pair=new bfspair(e.v,rpair.wsf+e.w,rpair.psf+" -> "+e.v);
                que.addLast(pair);
               }
            }

            if(que.getFirst()==null)
            {
                que.removeFirst();
                que.addLast(null);
                level++;
            }
        }
    }
    public static  void bfs_shortestpath_02(int src,boolean[] vis)
    {
    LinkedList<bfspair> que=new LinkedList <>();

    bfspair root=new bfspair(src,0,src+"");
    que.addLast(root);

    int des=6;
    int cyclecounter=0;
    boolean firstpath=false;
    int level=1;

    while(que.size()!=0)
    {
        //remove
        int size=que.size();
        while(size-->0)
        {
            bfspair rpair=que.removeFirst();

            //cycle
            if(vis[rpair.vtx])
            {
                System.out.println("Cycle Number: "+cyclecounter + ": "+rpair.psf );
                cyclecounter++;
            }
            //mark
            vis[rpair.vtx]=true;
            //destination
            if(rpair.vtx==des && !firstpath)
            {
                System.out.println(rpair.psf +"@"+rpair.wsf+"->"+level);
                firstpath=true;
            }
            //nbr
            for(Edge e: graph[rpair.vtx])
            {
                if(!vis[e.v])   //umarked nbr
                {
                 bfspair pair=new bfspair(e.v,rpair.wsf+e.w,rpair.psf+"->"+e.v);
                 que.addLast(pair);
                }
            }
        }
        level++;
    }
    }
    public static class primsPair implements Comparable<primsPair>{
        int vtx=0;  //vtx.
        int pvtx=0;  //perentVtx
        int wt=0;
        String psf="";
        primsPair(int vtx,int pvtx,int wt,String psf){
            this.vtx=vtx;
            this.pvtx=pvtx;
            this.wt=wt;    
            this.psf=psf;
        }
        @Override
        public int compareTo(primsPair o){
            return this.wt-o.wt;
        }
    }
    public static void prims(){
        int src=6;
        boolean[] visited=new boolean[graph.length];
        PriorityQueue<primsPair> pq=new PriorityQueue<>();
        pq.add(new primsPair(src,-1,0,src+""));
        while(!pq.isEmpty()){
            primsPair rpair=pq.remove();
            if(visited[rpair.vtx]) continue;
            if(rpair.pvtx!=-1) addEdge2(rpair.vtx,rpair.pvtx,rpair.wt);
            visited[rpair.vtx]=true;
            for(Edge e: graph[rpair.vtx]){
                if(!visited[e.v]){
                    pq.add(new primsPair(e.v,rpair.vtx,e.w,rpair.psf + " "+ e.v));
                }
            }
        }    
        display2();
    }
    public static int[] par=new int[graph.length];
    public static int[] size=new int[graph.length];
    public static int find(int vtx)
    {
        if(par[vtx]!=vtx)
        {
            par[vtx]=find(par[vtx]);
            return par[vtx];
        }
        else
        {
            return vtx;
        }
    }
    public static void union(int pt1,int pt2)
    {
        if(size[pt1]<=size[pt2])
        {
            par[pt1]=pt2;
            size[pt2]+=size[pt1];
        }
        else
        {
            par[pt2]=pt1;
            size[pt1]+=size[pt2];
        }
    }
    public static class kpair implements Comparable<kpair>{
        int v=0,u=0,w=0;
        kpair(int v,int u,int w)
        {
            this.v=v;
            this.u=u;
            this.w=w;
        }
       @Override
        public int compareTo(kpair o){
            return this.w-o.w;
        }
    }  
    public static void kruskal()
    {
        for(int i=0;i<par.length;i++){
            par[i]=i;
            size[i]=1;
        }
        PriorityQueue<kpair> que=new PriorityQueue();
        que.add(new kpair(0,0,0));
        while(!que.isEmpty())
        {
          kpair  rpair=que.remove();
            int pt1=find(rpair.u);
            int pt2=find(rpair.v);
            if(pt1 !=pt2)
            {
                addEdge2(rpair.u,rpair.v,rpair.w);
                union(pt1,pt2);
            }
        }
        display2();
    }
    
  public static int[] dis=new int[graph.length];
  public static int[] low=new int[graph.length];
  public static boolean[] points=new boolean[graph.length];
  public static int time=0;
public static int root=0;
public static void AP(int vtx,int parent){
    dis[vtx]=low[vtx]=time++;
    // int root=0;
    for(Edge e: graph[vtx]){
        if(dis[e.v]==-1)
        {
            par[e.v]=vtx;
            if(parent == -1)
               root++;
            AP(e.v,vtx);            
            if(low[vtx]<=low[e.v])
            {
                points[vtx]=true;
            }
            low[vtx]=Math.min(low[vtx],low[e.v]);
        }
        else if(par[vtx]!=e.v)
        {
            low[vtx]=Math.min(low[vtx],dis[e.v]);
        }
    }
}
    

}