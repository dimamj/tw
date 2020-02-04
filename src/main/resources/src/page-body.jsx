import {useState, useEffect} from 'react';
import axios from 'axios'
import WeatherInfo from "./weather-info";

function PageBody(props) {
  const [weatherInfo, setWeatherInfo] = useState();

  useEffect(() => {
    axios.get('/api/weather')
      .then((response) => {
        setWeatherInfo(response.data);
      })
  }, []);

  return (
    <div className='app-body'>
      {weatherInfo ? <WeatherInfo data={weatherInfo}/> : <div className='loader'></div>}
    </div>
  );
}

export default PageBody;