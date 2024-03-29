import { NavLink } from 'react-router-dom';
import { ReactComponent as OfferImg } from "../../assets/svg/offer-img.svg";
import FeaturesSlider from '../../components/FeaturesSlider/FeaturesSlider';

function HomePage() {
    const features = [
        {iconClass: 'bi bi-rocket-takeoff', title: 'Special title treatment', text: 'With supporting text below as a natural lead-in to additional content.'},
        {iconClass: 'bi bi-rocket-takeoff', title: 'Special title treatment', text: 'With supporting text below as a natural lead-in to additional content.'},
        {iconClass: 'bi bi-rocket-takeoff', title: 'Special title treatment', text: 'With supporting text below as a natural lead-in to additional content.'},
        {iconClass: 'bi bi-rocket-takeoff', title: 'Special title treatment', text: 'With supporting text below as a natural lead-in to additional content.'},
    ];

    return (
        <main>
            <section className="section offer-section">
                <div className="container">
                    <div className="row">
                        <div className="col-12 col-md-6 d-flex flex-column justify-content-center align-items-start">
                            <h1>Наша місія - надавати розвиток </h1>
                            <p>Настав час користуватись новими можливостями, а не обмежувати себе, свій бізнес або команду.</p>
                            <NavLink className="btn btn-primary" to="/">Розпочати</NavLink>
                        </div>
                        <div className="col-12 col-md-6 mt-5 mt-md-0">
                            <OfferImg className="offer-img"/>
                        </div>
                    </div>
                </div>
            </section>
            <section className='section features-section accent-bg'>
                <div className="container">
                    <div className="row">
                        <div className='col-12'>
                            <FeaturesSlider items={features}/>
                        </div>
                    </div>
                    <div className="row pt-3">
                        <div className='col-12 text-center'>
                            <NavLink className="btn btn-primary" to="/">Дізнатися більше</NavLink>
                        </div>
                    </div>
                </div>
            </section>
            <section className='section cta-section'>
                <div className="container">
                    <div className="row">
                        <div className="col-12 col-md-10 offset-md-1 col-lg-8 offset-lg-2 text-center">
                            <h1>Зроби СВІЙ крок до успіху разом з "GLOS"</h1>
                            <p>Найважчий завжди перший крок. Проєкт "GLOS" це дієві інструменти для 
                                навчання, для бізнесу та для життя.</p>
                            <NavLink className="btn btn-primary" to="/">Розпочати</NavLink>
                        </div>
                    </div>
                </div>
            </section>
        </main>
        
    );
  }
  
  export default HomePage;