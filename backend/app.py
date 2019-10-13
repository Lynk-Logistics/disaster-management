from flask import Flask
from flask_cors import CORS
from bees_controller import bees_blueprint

app = Flask(__name__, template_folder='static/html/')
CORS(app)
app.register_blueprint(bees_blueprint)


if __name__ == '__main__':
    app.run(debug=True)