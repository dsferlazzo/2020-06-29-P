package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	
	private PremierLeagueDAO dao;
	private Graph<Match, DefaultWeightedEdge> grafo;
	private Map<Integer, Match> mapMatchId;
	private List<Connettivita> maxGiocatoriPartite;	
	
	private List<Match> getMatchesByMonth(int mese){	//DA IMPLEMENTARE EVENTUALE MAPPA
		List<Match> partite = new ArrayList<Match>(this.dao.getMatchesByMonth(mese));
		this.mapMatchId = new HashMap<Integer, Match>();
		for(Match m : partite)
			mapMatchId.put(m.getMatchID(), m);
		return partite;
	}
	
	private List<Connettivita> getConnettivita(int mese, int min){
		return this.dao.getConnettivita(mese, min);
	}
	
	public String creaGrafo(int mese, int minuti) {
		
		this.dao = new PremierLeagueDAO();
		this.grafo = new SimpleWeightedGraph<Match, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		//AGGIUNGO I VERTICI
		Graphs.addAllVertices(grafo, this.getMatchesByMonth(mese));
		
		//AGGIUNGO GLI ARCHI
		
		List<Connettivita> archi = this.getConnettivita(mese, minuti);
		int maxPeso=0;
		for(Connettivita c : archi) {
			int peso = c.getPeso();
			Graphs.addEdge(grafo, this.mapMatchId.get(c.getMatchId1()),
					this.mapMatchId.get(c.getMatchId2()), peso);
			if(peso==maxPeso)
				maxGiocatoriPartite.add(c);
			if(peso>maxPeso) {
				maxPeso=peso;
				maxGiocatoriPartite= new ArrayList<Connettivita>();
				maxGiocatoriPartite.add(c);
			}
			
		}
		
		String result = "Grafo creato\n#VERTICI: " + this.grafo.vertexSet().size() +
				"\n#ARCHI: " + this.grafo.edgeSet().size();
		return result;
		
		
		
	}
	
	public String getMaxGiocatori() {
		if(this.grafo==null)
			return "Creare prima il grafo";
		String result = "Coppie con connessione massima:\n\n";
		for(Connettivita c : this.maxGiocatoriPartite) {
			Match m1 = this.mapMatchId.get(c.getMatchId1());
			Match m2 = this.mapMatchId.get(c.getMatchId2());
			int peso = c.getPeso();
			result += m1 + " - " + m2 + " (" + peso + ")\n";
		}
		
		return result;
		
		
	}
	
}
