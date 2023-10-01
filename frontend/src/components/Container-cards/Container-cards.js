import './Container-cards.css'
import Cards from '../Cards/Cards'
import Chat from '../Chat/Chat'
import { useEffect } from 'react';
import { useState } from 'react';

let intervalId=null;
const Container = (props) =>{

    const formatHora = (data) => {
        data= new Date(data);
        const horas = String(data.getHours()).padStart(2, '0');
        const minutos = String(data.getMinutes()).padStart(2, '0');
        const segundos = String(data.getSeconds()).padStart(2, '0');
        return `${horas}:${minutos}:${segundos}`;
    }
    const [cards, setCards]= useState([]);
    const [rodada, setRodada]= useState({});
    const [chosenCard, setChosenCard]= useState(null);
    const [tempoRestante, setTempoRestante] = useState('');
    useEffect(() => {
        setCards(props.cards);
        setRodada(props.rodada);
        setChosenCard(props.chosenCard);

    }, [])
    useEffect(() => {
        setCards(props.cards);
        setRodada(props.rodada);
        setChosenCard(props.chosenCard);
        console.log('asdasdas-=====',props.rodada);
        if(props.rodada.situacao!=='FINALIZADA'){
            let hora=new Date(props.rodada.hora);
            handleTimer(hora)
        }
            return () => clearInterval(intervalId);
           
    }, [props.cards, props.rodada, props.chosenCard])
    const handleTimer = (hora) => {
        intervalId = setInterval(() => {
            const agora = new Date().getTime();
            const dataFuturaTimestamp = hora.getTime();
            const diferenca = dataFuturaTimestamp - agora;
      
            if (diferenca <= 0) {
              clearInterval(intervalId);
              setTempoRestante('');
            } else {
              const minutosRestantes = Math.floor(diferenca / (1000));
              setTempoRestante(`${minutosRestantes}`);
            }
          }, 1000);
    }
    const renderReader = () => {
        if(rodada.situacao==='AGUARDANDO'){
            return <h2>Aguarde os outros jogadores, a partida começara automaticamente em {tempoRestante} segundos</h2>
        }
        return <h2>{tempoRestante}</h2>
    }
    return(
        <div>
            <div className='boxHora'>
                {renderReader()}
            </div>
            <div className="container-cards">
                {cards && cards.map((card, id) => (
                    <Cards  key={id} card={card} chosenCard={chosenCard} onSelectCard={props.onSelectCard}/>
                )
                )}
              
            </div>
        </div>
    )
}

export default Container