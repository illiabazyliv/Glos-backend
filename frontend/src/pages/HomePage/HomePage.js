import { NavLink } from 'react-router-dom';
import { Swiper, SwiperSlide } from 'swiper/react';
import { Pagination } from 'swiper/modules';
import 'swiper/css';
import 'swiper/css/pagination';
import { ReactComponent as OfferImg } from "../../assets/svg/offer-img.svg";

function HomePage() {
    return (
        <main>
            <section className="section offer-section">
                <div className="container">
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
                </div>
            </section>
            <section className='section features-section accent-bg'>
                <div className="container">
                    <div className="row">
                        <div className='col-12'>
                            <Swiper
                                modules={[Pagination]}
                                pagination={{ clickable: true }}
                                spaceBetween={30}
                                slidesPerView={1}
                                breakpoints={{
                                    576: {
                                        slidesPerView: 2,
                                    },
                                    992: {
                                        slidesPerView: 3,
                                    },
                                    1200: {
                                        slidesPerView: 4,
                                    },
                                }}
                                >
                                <SwiperSlide>
                                    <div className="card feature">
                                        <div className="card-body text-center">
                                            <div className='card-img d-flex align-items-center justify-content-center pt-3'>
                                                <div className='card-icon rounded-circle d-flex align-items-center justify-content-center'>
                                                    <i className='bi bi-rocket-takeoff'></i>
                                                </div>
                                            </div>
                                            <h5 className="card-title pb-1">Special title treatment</h5>
                                            <p className="card-text">With supporting text below as a natural lead-in to additional content.</p>
                                        </div>
                                    </div>
                                </SwiperSlide>
                                <SwiperSlide>
                                    <div className="card feature">
                                        <div className="card-body text-center">
                                            <div className='card-img d-flex align-items-center justify-content-center pt-3'>
                                                <div className='card-icon rounded-circle d-flex align-items-center justify-content-center'>
                                                    <i className='bi bi-rocket-takeoff'></i>
                                                </div>
                                            </div>
                                            <h5 className="card-title pb-1">Special title treatment</h5>
                                            <p className="card-text">With supporting text below as a natural lead-in to additional content.</p>
                                        </div>
                                    </div>
                                </SwiperSlide>
                                <SwiperSlide>
                                    <div className="card feature">
                                        <div className="card-body text-center">
                                            <div className='card-img d-flex align-items-center justify-content-center pt-3'>
                                                <div className='card-icon rounded-circle d-flex align-items-center justify-content-center'>
                                                    <i className='bi bi-rocket-takeoff'></i>
                                                </div>
                                            </div>
                                            <h5 className="card-title pb-1">Special title treatment</h5>
                                            <p className="card-text">With supporting text below as a natural lead-in to additional content.</p>
                                        </div>
                                    </div>
                                </SwiperSlide>
                                <SwiperSlide>
                                    <div className="card feature">
                                        <div className="card-body text-center">
                                            <div className='card-img d-flex align-items-center justify-content-center pt-3'>
                                                <div className='card-icon rounded-circle d-flex align-items-center justify-content-center'>
                                                    <i className='bi bi-rocket-takeoff'></i>
                                                </div>
                                            </div>
                                            <h5 className="card-title pb-1">Special title treatment</h5>
                                            <p className="card-text">With supporting text below as a natural lead-in to additional content.</p>
                                        </div>
                                    </div>
                                </SwiperSlide>
                            </Swiper>
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
                            <h1>Lorem ipsum dolor sit amet, consectetur</h1>
                            <p>Maecenas sagittis felis at accumsan sodales. Nunc viverra pulvinar ipsum, convallis blandit lacus congue id sagittis felis at accumsan sodales.</p>
                            <NavLink className="btn btn-primary" to="/">Розпочати</NavLink>
                        </div>
                    </div>
                </div>
            </section>
        </main>
        
    );
  }
  
  export default HomePage;