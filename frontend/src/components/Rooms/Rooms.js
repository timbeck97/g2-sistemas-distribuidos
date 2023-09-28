import { useEffect } from "react"
import './Rooms.css'
const Rooms = ({partidas, onCriarPartida,entrarPartida}) => {
    useEffect(() => {
        
    }, [])
    return (
        <div className="container main">
            <div className="row">
                <div className="col-md-12">
                    <button className="btn btn-secondary" onClick={()=>onCriarPartida()}>Criar Partida</button>
                </div>
            </div>
            <table className="table table-dark">
                <thead>
                    <tr>
                        <th scope="col">Id</th>
                        <th scope="col">Participantes</th>
                        <th scope="col"></th>
                    </tr>
                </thead>
                <tbody>
                    {partidas && partidas.map((partida) => (
                        <tr key={partida.id}>
                            <th scope="row">{partida.id}</th>
                            <td>{partida.jogadores.map(x=>x.nome).join(', ')}</td>
                            <td><button className="btn btn-success" onClick={()=>entrarPartida(partida.id)}>Entrar</button></td>
                        </tr>
                    ))}


                </tbody>
            </table>
        </div>
    )
}

export default Rooms