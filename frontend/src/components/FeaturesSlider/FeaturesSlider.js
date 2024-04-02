import { Swiper, SwiperSlide } from 'swiper/react';
import { Pagination } from 'swiper/modules';
import 'swiper/css';
import 'swiper/css/pagination';
import Feature from '../Feature/Feature';

function FeaturesSlider({items}) {
  return (
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
        {
            items.map(item => {
                return <SwiperSlide key={item.id}>
                    <Feature item={item}/>
                </SwiperSlide>
            })
        }
    </Swiper>
  );
}

export default FeaturesSlider;