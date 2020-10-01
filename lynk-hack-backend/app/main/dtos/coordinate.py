class Coordinate:
    def __init__(self, obj):
        self.lat = float(obj.get('lat', 1))
        self.long = float(obj.get('long', 1))
        self.radius = float(obj.get('radius', 1))
