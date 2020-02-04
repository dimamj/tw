import DayWeather from "./day-weather";

function WeatherInfo({data}) {
  const {geoIpData, weatherResult} = data;

  const dayWeatherList = weatherResult.days.map(
    ({date, hoursWeather}) => <DayWeather key={date} date={date} hoursWeather={hoursWeather}/>
  );

  return (
    <div className='weather'>
      <div className='weather__city'>
        {geoIpData.cityName}
      </div>

      <div className='weather__days-list'>
        {dayWeatherList}
      </div>
    </div>
  );
}

export default WeatherInfo;