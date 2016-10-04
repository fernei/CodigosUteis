/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teste;

import java.awt.event.WindowEvent;

/**
 *
 * @author fernando.m.souza
 */
public class WindowCloser extends java.awt.event.WindowAdapter{
  /**
   * Metodo invocato automaticamente alla chiusura della finestra cui e' agganciata
   * l'istanza di questa classe.
   * @param event evento di finestra
   */
  public void windowClosing(WindowEvent event)
    {
        System.out.println("Chiusura della finestra e dell'applicazione");
        System.out.println("Evento ricevuto: "+event);
        System.exit(0);
    }

}