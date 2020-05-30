#include<iostream>
#include<vector>
#include<stack>
#include<list>
using namespace std;
class directed{
    public:
    int v=0,w=0;
    directed(int v,int w)
    {
        this->v=v;
        this->w=w;
    }
    // directed()
    // {}
};
vector<vector< directed *>> dgraph;
void addedge(int u,int v,int w)
{
    if(u<0||v<0||u>=dgraph.size()||v>=dgraph.size())
    {
        return;
    }
    dgraph[u].push_back(new directed(v,w));
}
void display()
{
    for(int i=0;i<dgraph.size();i++)
    {
        cout<<i<<"->";
        for(int j=0;j<dgraph[i].size();j++)
        {
            cout<<"("<<dgraph[i][j]->v<<","<<dgraph[i][j]->w<<")";
        }
        cout<<endl;
    }
}
bool topologicalsort(int src,vector<int> &st,vector<bool>& vis,vector<bool> & cycle)  //acyclic graph
{
    bool res=false;
  for(int i=0;i<dgraph.size()&&!res;i++)
    {
        if(!vis[i])
        res=res||topologicalsort(src,st,vis,cycle);
    }

    for(int i=st.size()-1 && !res;i>=0;i--)
    {
        cout<<st[i]<<" ";
    } 
    if(res){
        return false;
    }

    return true;
}
bool topologicalsort_(int src,vector<int> &st,vector<bool>& vis,vector<bool> & cycle)
{
    vis[src]=true;
    cycle[src]=true;
    for(directed *e:dgraph[src])
    {
        if(!vis[e->v])
        topologicalsort_(src,st,vis,cycle);
       else if(vis[e->v]&&cycle[e->v]==true)
       return true;
    }
    cycle[src]=false;
    st.push_back(src);
    vis[src]=false;
}

int main()
{
    for(int i=0;i<7;i++)
    {
        vector<directed *> ar;
        dgraph.push_back(ar);
    }
    addedge(1,0,1);
    // addedge(2,1,1);
    // addedge(5,2,1);
    // addedge(7,5,1);
    // addedge(5,4,1);
    // addedge(7,6,1);
    // addedge(6,3,1);
    // addedge(3,1,1);
    // addedge(6,4,1);
    display();
//topologicalsort(7,)
    return 0;
}