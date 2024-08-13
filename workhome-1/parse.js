import axios from "axios";
import fs from "node:fs";
const fsPromise = fs.promises;

const Member_TABLE = "cd.members";
const Booking_TABLE = "cd.bookings";
const Facility_TABLE = "cd.facilities";

const mDML = new Map();

const FILE_NAME = "dml.sql";
const members_URL =
  "https://pgexercises.com/SQLForwarder?query=select+*+from+cd.members&writeable=0&tableToReturn=";
const bookings_URL =
  "https://pgexercises.com/SQLForwarder?query=select+*+from+cd.bookings&writeable=0&tableToReturn=";
const facilities_URL =
  "https://pgexercises.com/SQLForwarder?query=select+*+from+cd.facilities&writeable=0&tableToReturn=";

// escaped_schema - это заключение полей в кавычки которым они нужны(текст, дата)
mDML.set(Member_TABLE, { url: members_URL, escaped_schema: [0, 1, 1, 1, 0, 1, 0, 1] });
mDML.set(Booking_TABLE, { url: bookings_URL, escaped_schema: [0, 0, 0, 1, 0] });
mDML.set(Facility_TABLE, { url: facilities_URL, escaped_schema: [0, 1, 0, 0, 0, 0] });

function fetchAll() {
  const resp_members = axios.get(mDML.get(Member_TABLE).url);
  const resp_bookings = axios.get(mDML.get(Booking_TABLE).url);
  const resp_facilities = axios.get(mDML.get(Facility_TABLE).url);

  const _promises = [];

  _promises.push(resp_bookings, resp_members, resp_facilities);
  let isAllFetched = Promise.all(_promises);
  isAllFetched
    .then(async () => {
      mDML.get(Member_TABLE).data = (await resp_members).data;
      mDML.get(Booking_TABLE).data = (await resp_bookings).data;
      mDML.get(Facility_TABLE).data = (await resp_facilities).data;

      appendToDML(mDML, FILE_NAME);

    })
    .catch((err) => {
      console.log(err);
      process.exit(1);
    });
}

async function appendToDML(map, file_name) {
  const q_header = "INSERT INTO ";
  await fsPromise.writeFile(file_name, "");
  for (const [table_name, map_content] of map) {
    const table_data = map_content.data;
    let result = q_header + table_name;
    result += `(${table_data.headers.join(", ")}) \n\tVALUES`;

    for (const row of table_data.values) {

      for(let i = 0; i < row.length; i++){
        if(row[i].length == 0) row[i] = "null";
        if(map_content.escaped_schema[i] == 1){
          row[i] = `'${row[i]}'`;
        }
      }

      result += `(${row.join(", ")}),\n`;
    }
    try {
      await fsPromise.appendFile(file_name, result.slice(0, -2) + ";\n\n");
    } catch (e) {
      console.log(e);
      process.exit(1);
    }
  }
}

fetchAll();
