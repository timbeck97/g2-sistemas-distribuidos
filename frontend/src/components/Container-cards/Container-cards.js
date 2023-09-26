import './Container-cards.css'
import Cards from '../Cards/Cards'
import Chat from '../Chat/Chat'

const Container = () =>{
    return(
        <div className="container-cards">
            <Chat />
            <Cards />
            <Cards />
            <Cards />
            <Cards />
            <Cards />
        </div>
    )
}

export default Container