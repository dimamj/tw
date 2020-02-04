function DayWeather({date, hoursWeather}) {

  const hoursList = [...hoursWeather];
  const firstHour = hoursList[0].hour;

  for (let i = 0; i < firstHour; i += 3) {
    hoursList.push({hour: i});
  }

  hoursList.sort((a, b) => a.hour - b.hour);

  return (
    <div className='day-weather'>
      <div className='day-weather__date'>{date}</div>

      <table className='day-weather__info'>
        <tbody>
        <tr className='day-weather__hour'>
          <td className='day-weather__title'>Hour</td>
          {hoursList.map((hw) => <td key={hw.hour}>{hw.hour}</td>)}
        </tr>
        <tr className='day-weather__weather-type'>
          <td className='day-weather__title'></td>
          {hoursList.map((hw) => hw.weatherType ?
            <td key={hw.hour}><img src={`/img/weather-icon/${hw.weatherType}.png`}/></td> : <td key={hw.hour}>n/d</td>)}
        </tr>
        <tr className='day-weather__temp'>
          <td className='day-weather__title'>Temperature °C</td>
          {hoursList.map((hw) => hw.temperature !== undefined ? <td key={hw.hour}>{hw.temperature}°</td> : <td key={hw.hour}></td>)}
        </tr>
        <tr className='day-weather__wind-speed'>
          <td className='day-weather__title'>Wind speed (m/s)</td>
          {hoursList.map((hw) => hw.windSpeed !== undefined ? <td key={hw.hour}>{hw.windSpeed}</td> : <td key={hw.hour}></td>)}
        </tr>
        <tr className='day-weather__precipitation'>
          <td className='day-weather__title'>Precipitation (cm)</td>
          {hoursList.map((hw) => hw.precipitationInCm !== undefined ? <td key={hw.hour}>{hw.precipitationInCm}</td> :
            <td key={hw.hour}></td>)}
        </tr>
        </tbody>
      </table>
    </div>
  );
}

export default DayWeather;