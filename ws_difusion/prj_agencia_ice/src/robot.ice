///////////////////////////////////////////////////////////////////////
//// Module agencia (simplificado para pruebas de difusión)

module agencia {

///////////////////////////////////////////////////////////////////////
//// Module agencia.datos

  module datos {
  
     struct EstadoRobot {
       string nombre;
       int id;
       string IORrob;  //Referencia en formato String IOR
     }

     sequence<EstadoRobot> ListaEstadosRobot;

     struct Instantanea {
        ListaEstadosRobot estadorobs;
     }
      
     struct IPYPort{
        string ip;
        int port;
     }

     struct suscripcion {
         int id;
         IPYPort iport;
     }
}

///////////////////////////////////////////////////////////////////////
//// Module agencia.objetos

  module objetos {
  
    interface RobotSeguidor{
       agencia::datos::EstadoRobot ObtenerEstado( );
    };  

    interface Camara{
        agencia::datos::suscripcion SuscribirRobot(string IORrob);
    };
  }
}

