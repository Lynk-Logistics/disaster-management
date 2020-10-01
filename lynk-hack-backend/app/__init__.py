from app.main.routes.victims import api as victims_ns
from app.main.routes.volunteers import api as volunteers_ns
from app.main.routes.essentials import api as essentials_ns
from app.main.routes.issues import api as issues_ns
from app.main.routes.donors import api as donor_ns

from app.main.api import api


api.add_namespace(victims_ns, path="/")
api.add_namespace(volunteers_ns, path="/")
api.add_namespace(essentials_ns, path="/")
api.add_namespace(issues_ns, path="/")
api.add_namespace(donor_ns, path="/")
