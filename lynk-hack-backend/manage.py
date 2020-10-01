from flask import Flask
from app.main.api import blueprint
from flask_cors import CORS

app = Flask(__name__)
cors = CORS(app)

if __name__ == '__main__':
    app.register_blueprint(blueprint)
    app.run(host='0.0.0.0')
