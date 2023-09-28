import React, { useEffect, useState } from 'react';
import './Cards.css';

function Cards({onSelectCard, card}) {
  const [isActive, setIsActive] = useState(false);
  const [isBlocked, setIsBlocked] = useState(false);
  useEffect(() => {
    console.log(card);
  },[])
  const toggleCard = () => {
    if (!isBlocked) {
      setIsBlocked(true);
      setIsActive(!isActive);
    }
    onSelectCard('card selected')
  };

  return (
    <div className={`flip-container ${isActive ? 'active' : ''}`} onClick={toggleCard}>
      <div className={`container-cards ${isBlocked ? 'blocked' : ''}`}>
        <div className="cards">
          <div className="back">
            <div className="card-content-back">
              <h2>Gire sua sorte.</h2>
            </div>
          </div>
          <div className="front">
            <div className="card-content-front">
              <h2>Você tirou o numero</h2>
              <p className='titleCard'><strong>{card.value}</strong></p>
              <p>Aguarde os outros jogadores</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Cards;
