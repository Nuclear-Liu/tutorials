import axios from 'axios'

const axiosInstance = axios.create({
  baseURL:'',
  timeout: 2_000
})

export default axiosInstance;
