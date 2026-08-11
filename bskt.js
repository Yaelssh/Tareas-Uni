//Gestor de torneos de baloncesto (especificamente para los jugadores)
const prompt = require('prompt-sync')();
const NomTorneo = "torneo 3x3";
const MaxJug = 12;
let ListJugador = [];

function datosvalidos(valor) {
    let num = Number(valor);
    return !isNaN(num) && num >= 0
}
function Showmvp() {
    if (ListJugador.length === 0) {
        console.log('No hay jugadores registrados')
        return 0;
    }
    let mvp = ListJugador[0]
    for (let i = 1; i < ListJugador.length; i++) {
        if (ListJugador[i].puntos > mvp.puntos) {
            mvp = ListJugador[i];
        }
    }
    console.log('El mvp es ', mvp.nombre, ' con ', mvp.puntos, ' puntos')
}
function Aggjugador(id, nombre, edad, pts, fouls, posicion) {
    let NewJugador = {
        id: id,
        nombre: nombre,
        edad: edad,
        posicion: posicion,
        puntos: pts,
        faltas: fouls
    };
    ListJugador.push(NewJugador);
    console.log('Jugador ', nombre, ' registrado con exito');
}
function stats() {
    if (ListJugador.length === 0) {
        console.log('No hay jugadores registrados')
        return 0;
    }
    console.log('---Estadisticas---');
    for (let jugador of ListJugador) {
        console.log('ID: ', jugador.id, ' | Nombre: ', jugador.nombre, ' | edad: ', jugador.edad, ' | Puntos: ', jugador.puntos, ' | Faltas: ', jugador.faltas, ' | Posición: ', jugador.posicion);
    }

}

function menu() {
    let opcion = 0;
    while (opcion !== 5) {
        console.log("---MENÚ PRINCIPAL---")
        console.log("1. Registrar jugador")
        console.log('2. Ver estadisticas')
        console.log("3. Buscar jugador")
        console.log('4. Mostrar MVP')
        console.log('5. Salir')

        opcion = parseInt(prompt("ingresa la opción deseada: "))
        switch (opcion) {
            case 1:
                let id = ListJugador.length + 1;
                let nombre = prompt('Ingrese el nombre del jugador: ')
                let edad = prompt('Ingresa la edad del jugador: ')
                let pts = prompt('Ingresa los puntos anotados: ')
                let fouls = prompt('Ingresa las faltas anotadas: ')
                let posicion = prompt('ingresa la posición del jugador: ')
                if (datosvalidos(edad) && datosvalidos(pts) && datosvalidos(fouls)) {

                    Aggjugador(id, nombre, Number(edad), Number(pts), Number(fouls), posicion);

                } else {
                    console.log('Los datos deben ser validos (nada de letras o negativos)');
                }
                break;
            case 2:
                stats();
                break;
            case 3:
                let Searchid = parseInt(prompt('Ingresa la id del jugador: '));
                let Find = false;
                let i = 0;
                while (!Find && i < ListJugador.length) {
                    let j = ListJugador[i];
                    if (j.id === Searchid) {
                        console.log('El jugador es: ', j.nombre, ' sus puntos fueron: ', j.puntos)
                        Find = true;
                    }
                    i++
                }
                if (!Find) {
                    console.log('Id no encontrada, usuario inhexistente')
                }
                break;
            case 4:
                Showmvp();
                break;

            case 5:
                console.log('saliendo del menu')
                break;

            default:
                console.log('opcion invalida, intente de nuevo');
                break;
        }
    }
}
menu()