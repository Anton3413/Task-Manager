function updateHeaderTime() {
    const now = new Date();

    const days = ["Sun","Mon","Tue","Wed","Thu","Fri","Sat"];
    const months = ["Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"];

    const day = days[now.getDay()];
    const date = now.getDate();
    const month = months[now.getMonth()];
    const hours = now.getHours().toString().padStart(2,'0');
    const minutes = now.getMinutes().toString().padStart(2,'0');

    const formatted = `${day} ${date} ${month} ${hours}:${minutes}`;
    const el = document.getElementById("local-time-header");
    if(el) el.textContent = formatted;
}

updateHeaderTime();
setInterval(updateHeaderTime, 60000);
