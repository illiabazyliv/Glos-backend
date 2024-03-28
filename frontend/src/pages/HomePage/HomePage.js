import { NavLink } from 'react-router-dom';
import { ReactComponent as OfferImg } from "../../assets/svg/offer-img.svg";

function HomePage() {
    return (
        <div className="container">
            <section className="offer-section">
                <div className="row">
                    <div className="col-12 col-md-6 d-flex flex-column justify-content-center align-items-start">
                        <h1>Lorem ipsum dolor sit amet, consectetur</h1>
                        <p>Maecenas sagittis felis at accumsan sodales. Nunc viverra pulvinar ipsum, convallis blandit lacus congue id.</p>
                        <NavLink className="btn btn-primary" to="/">Розпочати</NavLink>
                    </div>
                    <div className="col-12 col-md-6 mt-5 mt-md-0">
                        <OfferImg className="offer-img"/>
                    </div>
                </div>
            </section>
        </div>
    );
  }
  
  export default HomePage;