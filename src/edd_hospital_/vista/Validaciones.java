/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edd_hospital_.vista;

import java.awt.event.KeyEvent;

/**
 *
 * @author ivanh
 */
public class Validaciones
{

    public static void eliminaCopyPasteCut(KeyEvent e)
    {
        if (e.isControlDown())
        {
            switch (e.getKeyCode())
            {
                case KeyEvent.VK_C: // Ctrl+C
                case KeyEvent.VK_V: // Ctrl+V
                case KeyEvent.VK_X: // Ctrl+X
                    e.consume();  // Cancela la acción por defecto
                    System.out.println("Atajo bloqueado: Ctrl+" + KeyEvent.getKeyText(e.getKeyCode()));
                    break;
            }
        }
    }
}
