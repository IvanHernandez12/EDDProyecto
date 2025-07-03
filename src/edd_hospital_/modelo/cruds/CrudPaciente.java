/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edd_hospital_.modelo.cruds;

import cjb.ci.Mensaje;
import edd_hospital_.modelo.Especialidad;
import edd_hospital_.modelo.RemodelacionHospitales;
import edd_hospital_.multi_lista.MultiListaDL;
import edd_hospital_.multi_lista.NodoML;
import interfaces.Crudable;
import javax.swing.JOptionPane;

/**
 *
 * @author HP
 */
public class CrudPaciente implements Crudable
{

    @Override
    public void insertar(MultiListaDL multilista, NodoML nodoAInsertar, String... ruta)
    {
        validar(nodoAInsertar, multilista, ruta);
        multilista.inserta(nodoAInsertar, ruta);
        if (RemodelacionHospitales.esTemporal(ruta[0], ruta[1]))
        {
            JOptionPane.showMessageDialog(null, "El hospital esta en remodelación se a agregado al hospital temporal" + ruta[1]);
        }
    }

    @Override
    public NodoML eliminar(MultiListaDL multilista, String... ruta)
    {
        if (RemodelacionHospitales.esTemporal(ruta[0], ruta[1]))
        {
            JOptionPane.showMessageDialog(null, "Se elimino el paciente " + ruta[3] + "\nEn el " + ruta[1]);
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
        if (nodoAActualizar != null)
        {
            nodoAActualizar.setObj(nuevo);
        }
        if (RemodelacionHospitales.esTemporal(ruta[0], ruta[1]))
        {
            JOptionPane.showMessageDialog(null, "Se actualizo el paciente " + ruta[3] + "\nEn el " + ruta[1]);
        }
    }

    public void validar(NodoML paciente, MultiListaDL multilista, String ruta[])
    {
        NodoML especialidadPadre = multilista.buscarEnMultilista(ruta);
        if (especialidadPadre == null)
        {
            throw new IllegalStateException("No hay especialidad padre");
        }
        if (especialidadPadre.getObj() instanceof Especialidad e)
        {
            if (e.getNumeroDeCamas() == Especialidad.getNumeroDePacientes(especialidadPadre)
                    && Especialidad.getNumeroDePacientes(especialidadPadre) != 0)
            {
                throw new IllegalStateException("No hay camas disponibles");

            }

        }
        if (Crudable.existeEseNombreEnRuta(multilista, paciente, ruta))
        {

            throw new IllegalArgumentException("ya existe un paciente con ese nombre ");
        }
    }

}
