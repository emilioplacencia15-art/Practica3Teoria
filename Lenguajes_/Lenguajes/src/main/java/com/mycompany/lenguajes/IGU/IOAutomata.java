package com.mycompany.lenguajes.IGU;

import java.io.*;
import java.util.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.w3c.dom.*;

public class IOAutomata {

    // --- MÉTODOS JSON ---
    public static void exportarJSON(Automata aut, File file) throws Exception {
        PrintWriter pw = new PrintWriter(file);
        pw.println("{");
        pw.println("  \"estados\": [");
        for (int i = 0; i < aut.estados.size(); i++) {
            Estado e = aut.estados.get(i);
            pw.println("    {\"nombre\":\"" + e.nombre + "\", \"x\":" + e.x + ", \"y\":" + e.y + 
                       ", \"inicial\":" + e.esInicial + ", \"final\":" + e.esFinal + "}" + 
                       (i < aut.estados.size() - 1 ? "," : ""));
        }
        pw.println("  ],");
        pw.println("  \"transiciones\": [");
        for (int i = 0; i < aut.transiciones.size(); i++) {
            Transicion t = aut.transiciones.get(i);
            pw.println("    {\"origen\":\"" + t.origen.nombre + "\", \"destino\":\"" + 
                       t.destino.nombre + "\", \"simbolo\":\"" + t.simbolo + "\"}" + 
                       (i < aut.transiciones.size() - 1 ? "," : ""));
        }
        pw.println("  ]");
        pw.println("}");
        pw.close();
    }

    public static Automata importarJSON(File file) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(file));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) sb.append(line);
        br.close();
        
        String json = sb.toString();
        ArrayList<Estado> estados = new ArrayList<>();
        ArrayList<Transicion> transiciones = new ArrayList<>();
        String[] partes = json.split("\\{");
        
        for (String p : partes) {
            if (p.contains("\"nombre\"") && !p.contains("\"origen\"")) {
                String nombre = extraer(p, "nombre");
                int x = Integer.parseInt(extraer(p, "x"));
                int y = Integer.parseInt(extraer(p, "y"));
                Estado e = new Estado(x, y, nombre);
                e.esInicial = Boolean.parseBoolean(extraer(p, "inicial"));
                e.esFinal = Boolean.parseBoolean(extraer(p, "final"));
                estados.add(e);
            }
        }
        for (String p : partes) {
            if (p.contains("\"origen\"")) {
                Estado origen = buscarPorNombre(estados, extraer(p, "origen"));
                Estado destino = buscarPorNombre(estados, extraer(p, "destino"));
                if (origen != null && destino != null) 
                    transiciones.add(new Transicion(origen, destino, extraer(p, "simbolo")));
            }
        }
        return new Automata(estados, transiciones, estados.stream().filter(e->e.esInicial).findFirst().orElse(null));
    }

    // --- MÉTODOS XML GENÉRICO (TU FORMATO) ---
    public static void exportarXML(Automata aut, File file) throws Exception {
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        Element root = doc.createElement("automata");
        doc.appendChild(root);
        for (Estado e : aut.estados) {
            Element est = doc.createElement("estado");
            est.setAttribute("nombre", e.nombre);
            est.setAttribute("x", String.valueOf(e.x));
            est.setAttribute("y", String.valueOf(e.y));
            est.setAttribute("inicial", String.valueOf(e.esInicial));
            est.setAttribute("final", String.valueOf(e.esFinal));
            root.appendChild(est);
        }
        for (Transicion t : aut.transiciones) {
            Element tr = doc.createElement("transicion");
            tr.setAttribute("origen", t.origen.nombre);
            tr.setAttribute("destino", t.destino.nombre);
            tr.setAttribute("simbolo", t.simbolo);
            root.appendChild(tr);
        }
        guardarXML(doc, file);
    }

    public static Automata importarXML(File file) throws Exception {
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
        ArrayList<Estado> estados = new ArrayList<>();
        ArrayList<Transicion> transiciones = new ArrayList<>();
        NodeList nEst = doc.getElementsByTagName("estado");
        for (int i=0; i<nEst.getLength(); i++) {
            Element e = (Element) nEst.item(i);
            Estado est = new Estado(Integer.parseInt(e.getAttribute("x")), Integer.parseInt(e.getAttribute("y")), e.getAttribute("nombre"));
            est.esInicial = Boolean.parseBoolean(e.getAttribute("inicial"));
            est.esFinal = Boolean.parseBoolean(e.getAttribute("final"));
            estados.add(est);
        }
        NodeList nTr = doc.getElementsByTagName("transicion");
        for (int i=0; i<nTr.getLength(); i++) {
            Element t = (Element) nTr.item(i);
            Estado o = buscarPorNombre(estados, t.getAttribute("origen"));
            Estado d = buscarPorNombre(estados, t.getAttribute("destino"));
            if (o != null && d != null) transiciones.add(new Transicion(o, d, t.getAttribute("simbolo")));
        }
        return new Automata(estados, transiciones, estados.stream().filter(e->e.esInicial).findFirst().orElse(null));
    }

    // --- MÉTODOS JFF (COMPATIBLE CON JFLAP) ---
    public static void exportarJFF(Automata aut, File file) throws Exception {
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        Element root = doc.createElement("structure");
        doc.appendChild(root);
        Element type = doc.createElement("type"); type.setTextContent("fa"); root.appendChild(type);
        Element automaton = doc.createElement("automaton"); root.appendChild(automaton);

        for (int i = 0; i < aut.estados.size(); i++) {
            Estado e = aut.estados.get(i);
            Element s = doc.createElement("state");
            s.setAttribute("id", String.valueOf(i));
            s.setAttribute("name", e.nombre);
            Element x = doc.createElement("x"); x.setTextContent(String.valueOf((double)e.x)); s.appendChild(x);
            Element y = doc.createElement("y"); y.setTextContent(String.valueOf((double)e.y)); s.appendChild(y);
            if (e.esInicial) s.appendChild(doc.createElement("initial"));
            if (e.esFinal) s.appendChild(doc.createElement("final"));
            automaton.appendChild(s);
        }
        for (Transicion tr : aut.transiciones) {
            Element t = doc.createElement("transition");
            Element f = doc.createElement("from"); f.setTextContent(extraerDigitos(tr.origen.nombre)); t.appendChild(f);
            Element to = doc.createElement("to"); to.setTextContent(extraerDigitos(tr.destino.nombre)); t.appendChild(to);
            Element r = doc.createElement("read"); r.setTextContent(tr.simbolo.equals("λ") ? "" : tr.simbolo); t.appendChild(r);
            automaton.appendChild(t);
        }
        guardarXML(doc, file);
    }

    public static Automata importarJFF(File file) throws Exception {
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
        ArrayList<Estado> estados = new ArrayList<>();
        ArrayList<Transicion> transiciones = new ArrayList<>();
        NodeList nEst = doc.getElementsByTagName("state");
        for (int i=0; i<nEst.getLength(); i++) {
            Element e = (Element) nEst.item(i);
            String id = e.getAttribute("id");
            String nom = e.getAttribute("name").isEmpty() ? "q"+id : e.getAttribute("name");
            double x = Double.parseDouble(e.getElementsByTagName("x").item(0).getTextContent());
            double y = Double.parseDouble(e.getElementsByTagName("y").item(0).getTextContent());
            Estado est = new Estado((int)x, (int)y, nom);
            est.esInicial = e.getElementsByTagName("initial").getLength() > 0;
            est.esFinal = e.getElementsByTagName("final").getLength() > 0;
            estados.add(est);
        }
        NodeList nTr = doc.getElementsByTagName("transition");
        for (int i=0; i<nTr.getLength(); i++) {
            Element t = (Element) nTr.item(i);
            String fId = t.getElementsByTagName("from").item(0).getTextContent();
            String tId = t.getElementsByTagName("to").item(0).getTextContent();
            String sym = t.getElementsByTagName("read").item(0).getTextContent();
            Estado o = buscarPorNombre(estados, "q"+fId); // JFLAP usa IDs
            Estado d = buscarPorNombre(estados, "q"+tId);
            transiciones.add(new Transicion(o, d, sym.isEmpty() ? "λ" : sym));
        }
        return new Automata(estados, transiciones, estados.stream().filter(e->e.esInicial).findFirst().orElse(null));
    }

    // --- AUXILIARES ---
    private static void guardarXML(Document doc, File file) throws Exception {
        Transformer t = TransformerFactory.newInstance().newTransformer();
        t.setOutputProperty(OutputKeys.INDENT, "yes");
        t.transform(new DOMSource(doc), new StreamResult(file));
    }
    private static Estado buscarPorNombre(ArrayList<Estado> lista, String nombre) {
        return lista.stream().filter(e -> e.nombre.equals(nombre) || e.nombre.equals("q"+nombre)).findFirst().orElse(null);
    }
    private static String extraer(String txt, String c) {
        int i = txt.indexOf("\""+c+"\":") + c.length() + 3;
        int j = txt.indexOf(",", i);
        if (j == -1 || txt.indexOf("}", i) < j) j = txt.indexOf("}", i);
        return txt.substring(i, j).replace("\"", "").trim();
    }
    private static String extraerDigitos(String s) { return s.replaceAll("[^0-9]", ""); }
}