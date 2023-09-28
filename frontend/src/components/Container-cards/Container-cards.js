import './Container-cards.css'
import Cards from '../Cards/Cards'
import Chat from '../Chat/Chat'

const Container = ({onSelectCard, cards}) =>{
    console.log(cards);
    return(
        <div className="container-cards">
            {cards.map((card) => (
                <Cards card={card} onSelectCard={onSelectCard}/>
            )
            )}
             {/* <Cards onSelectCard={onSelectCard}/>
             <Cards onSelectCard={onSelectCard}/>
             <Cards onSelectCard={onSelectCard}/>
            <Cards onSelectCard={onSelectCard}/>
             <Cards onSelectCard={onSelectCard}/> */}
        </div>
    )
}

export default Container