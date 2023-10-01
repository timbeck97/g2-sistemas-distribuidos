import React, { useEffect, useState } from 'react';
import './Cards.css';

function Cards({onSelectCard, card, chosenCard}) {
  const [isActive, setIsActive] = useState(false);
  const [isBlocked, setIsBlocked] = useState(false);
  const [isChosen, setIsChosen] = useState(false);
  const [value, setValue] = useState('');
  useEffect(() => {

    setIsChosen(chosenCard!==null);
    if(!chosenCard){
      setIsBlocked(false);
      setIsActive(false);
    }
   
  },[chosenCard])
  useEffect(() => {
    setTimeout(() => {
      setValue(card.value);
    }, 500);
    
  },[card.value])
  const toggleCard = (value) => {
    if (!isBlocked) {
      setIsBlocked(true);
      setIsActive(!isActive);
    }
    onSelectCard(value);
  };
  
  return (
    <div className={`flip-container ${isActive ? 'active' : ''}`} style={{cursor:`${!isChosen ?'pointer':''}`}} onClick={isChosen?()=>{}:()=>toggleCard(card.value)}>
      <div className={`container-cards ${isBlocked ? 'blocked' : ''}`}>
        <div className="cards">
          <div className={`back`}>
            <div className={`card-content-back`}>
              <h2>Gire sua sorte.</h2>
            </div>
          </div>
          <div className={`front`}>
            <div className="card-content-front">
              <h2>Você tirou o numero</h2>
              <p className='titleCard'><strong>{value}</strong></p>
              <p>Aguarde os outros jogadores</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Cards;
