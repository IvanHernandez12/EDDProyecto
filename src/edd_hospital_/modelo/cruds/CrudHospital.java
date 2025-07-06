/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edd_hospital_.modelo.cruds;

import edd_hospital_.modelo.Hospitales;
import edd_hospital_.modelo.LogicaNegocioJose;
import edd_hospital_.modelo.RemodelacionHospitales;
import edd_hospital_.multi_lista.MultiListaDL;
import edd_hospital_.multi_lista.NodoML;
import interfaces.Crudable;
import javax.swing.JOptionPane;

/**
 *
 * @author HP
 */
public class CrudHospital implements Crudable
{

    @Override
    public void insertar(MultiListaDL multilista, NodoML nodoAInsertar, String... ruta)
    {
        if (Crudable.existeEseNombreEnRuta(multilista, nodoAInsertar, ruta))
        {

            throw new IllegalArgumentException("ya existe un hospital con ese nombre ");
        }
        if (nodoAInsertar.getEt().equals("eliminar1234"))
        {

            throw new IllegalArgumentException("No puedes colocar ese nombre a un hospital ");
        }

        multilista.inserta(nodoAInsertar, ruta);
    }

    @Override
    public NodoML eliminar(MultiListaDL multilista, String... ruta)
    {
        String hospitalOrigen = RemodelacionHospitales.buscaSuOrigen(ruta[0], ruta[1]);
        if (hospitalOrigen != null)
        {
            multilista.elimina(ruta[0], hospitalOrigen);
        }
        return multilista.elimina(ruta);
    }

    @Override
    public NodoML buscarConRutaDeEtiquetas(MultiListaDL multilista, String... ruta)
    {
        return multilista.buscarEnMultilista(ruta);
    }

    @Override
    public void actualizarNodo(MultiListaDL multilista, Object nuevo, String... ruta)
    {
        NodoML nodoAActualizar = multilista.buscarEnMultilista(ruta);
        if (nodoAActualizar == null)
        {
            throw new IllegalArgumentException("No se encontro el nodo a actualizar");
        }
        if (nuevo == null)
        {
            throw new IllegalArgumentException("El nuevo objeto no puede ser null");
        }
        nodoAActualizar.setObj(nuevo);
        String hospitalOrigen = RemodelacionHospitales.buscaSuOrigen(ruta[0], ruta[1]);
        if (hospitalOrigen != null)
        {
            NodoML nodoHospitalOrigen = multilista.buscarEnMultilista(ruta[0], hospitalOrigen);
            if (nodoHospitalOrigen != null)
            {
                if (nodoHospitalOrigen.getObj() instanceof Hospitales objetoOriginal && nuevo instanceof Hospitales objetoNuevo)
                {
                    Hospitales nuev = new Hospitales(objetoNuevo.getDireccion(), objetoNuevo.getNivel(), objetoOriginal.getNombre(), objetoOriginal.getClaveH());
                    nodoHospitalOrigen.setObj(nuev);
                }

            } else
            {
                throw new IllegalStateException("No se encontro el hospital origen");
            }
            System.out.println("temporal" + nodoAActualizar.getEt());
            System.out.println("origen " + hospitalOrigen);

        }
        if (nuevo instanceof Hospitales h)
        {
            int nivel = h.getNivel();
            if (nivel == 3)
            {
                if (!LogicaNegocioJose.tieneOncoYNeuro(nodoAActualizar))
                {
                    LogicaNegocioJose.subirNodoANivel3(nodoAActualizar);
                    JOptionPane.showMessageDialog(null, "Se subio a nivel tres el hospital: " + nodoAActualizar.getEt());
                }
            } else
            {
                if (LogicaNegocioJose.tieneOncoYNeuro(nodoAActualizar))
                {
                    LogicaNegocioJose.bajarHospitalNivel3(nodoAActualizar, multilista);
                    JOptionPane.showMessageDialog(null, "Se bajo de nivel tres el hospital: \n" + nodoAActualizar.getEt());

                }
            }
        }
    }

}
