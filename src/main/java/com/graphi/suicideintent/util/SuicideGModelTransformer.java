///================================================
//  Kyle Russell
//  AUT University 2015
//  https://github.com/denkers/graphi-suic-plugin
//================================================

package com.graphi.suicideintent.util;

import cern.colt.matrix.impl.SparseDoubleMatrix2D;
import com.graphi.suicideintent.SuicideIntentPlugin;
import com.graphi.util.Edge;
import com.graphi.util.Node;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections15.Transformer;

public class SuicideGModelTransformer implements Transformer<Graph<Node, Edge>, SparseDoubleMatrix2D>
{
    private final int perspectiveIndex;
    private List<Node> deadNodes;
    
    public SuicideGModelTransformer(int perspectiveIndex, List<Node> deadNodes)
    {
        this.perspectiveIndex   =   perspectiveIndex;
        this.deadNodes          =   deadNodes;
    }
    
    @Override
    public SparseDoubleMatrix2D transform(Graph<Node, Edge> g)
    {
        int n                           =   g.getVertexCount();
        SparseDoubleMatrix2D matrix     =   new SparseDoubleMatrix2D(n, n);
        
        List<Node> vertices   =  new ArrayList<>(g.getVertices());
        for(int row = 0; row < n; row++)
        {
            Node current    =   vertices.get(row);
            for(int col = 0; col < n; col++)
            {
                Node next   =   vertices.get(col);
                
                if(deadNodes.contains(next))
                    matrix.set(row, col, SuicideIntentPlugin.CONFIG.getDeadWeight());
                
                else if(current.getID() == perspectiveIndex && next.getID() == perspectiveIndex)
                    matrix.set(row, col, SuicideIntentPlugin.CONFIG.getSelfWeight());
                
                else
                {
                    if(g.isNeighbor(current, next))
                    {
                        Edge edge   =   g.findEdge(current, next);
                        if(edge.getEdgeType() == EdgeType.UNDIRECTED)
                            matrix.set(row, col, SuicideIntentPlugin.CONFIG.getDirectedWeight());
                        else
                            matrix.set(row, col, SuicideIntentPlugin.CONFIG.getDirectedWeight());
                    }
                    
                    else matrix.set(row, col, 0);
                }
            }
        }
        
        return matrix;
    }
}
