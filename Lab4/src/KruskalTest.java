import java.util.Iterator;

public class KruskalTest {

	public static void main(String[] args) {
		DirectedGraph<Edge> graph = new DirectedGraph<>(6);
		graph.addEdge(new Edge(0, 1) {
			
			@Override
			public double getWeight() {
				return 1;
			}
		});
		
		graph.addEdge(new Edge(1, 3) {
			
			@Override
			public double getWeight() {
				// TODO Auto-generated method stub
				return 5;
			}
		});
		
		graph.addEdge(new Edge(1, 5) {
			
			@Override
			public double getWeight() {
				// TODO Auto-generated method stub
				return 15;
			}
		});
		
		graph.addEdge(new Edge(2, 3) {
			
			@Override
			public double getWeight() {
				// TODO Auto-generated method stub
				return 2;
			}
		});
		
		graph.addEdge(new Edge(3, 1) {
			
			@Override
			public double getWeight() {
				// TODO Auto-generated method stub
				return 10;
			}
		});
		
		graph.addEdge(new Edge(3, 4) {
			
			@Override
			public double getWeight() {
				// TODO Auto-generated method stub
				return 3;
			}
		});
		
		graph.addEdge(new Edge(4, 5) {
			
			@Override
			public double getWeight() {
				// TODO Auto-generated method stub
				return 4;
			}
		});
		
		Iterator<Edge> it = graph.minimumSpanningTree();
		System.out.println(it.toString());
	}

}
