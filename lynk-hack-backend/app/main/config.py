import os


class Config:
    DB_NAME = 'lynk-hack' if os.getenv('APP_ENV', 'dev') == 'dev' else 'disaster-management'
    DB_URL = os.getenv('DB_URL', 'mongodb://localhost:27017/admin')
