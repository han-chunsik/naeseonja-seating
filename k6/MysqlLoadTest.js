import sql from 'k6/x/sql';
import driver from "k6/x/sql/driver/mysql";
const db = sql.open(driver, 'application:application@tcp(localhost:3306)/hhplus');

export let options = {
    vus: 100,
    duration: '30s'
};

export default function () {
    let results = db.query('SELECT * FROM concert WHERE id = 1;');
    console.log(`Fetched ${results.length} rows`);
}

export function teardown() {
    db.close();
}