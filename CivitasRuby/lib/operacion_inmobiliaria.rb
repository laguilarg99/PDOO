# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

class Operacion_inmobiliaria
  
  def initialize(gest, ip)
    @gestion = gest
    @propiedad = ip
  end
  
  def getGestion
    return @gestion
  end
  
  def getPropiedad
    return @propiedad
  end
end
