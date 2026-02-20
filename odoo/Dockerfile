FROM odoo:17.0

USER root

# Install Python dependencies for all custom addons
COPY extra-addons/convive-jwt/requirements.txt /tmp/convive-jwt-requirements.txt
RUN pip3 install --no-cache-dir -r /tmp/convive-jwt-requirements.txt

USER odoo

# Use custom config file
CMD ["odoo", "-c", "/etc/odoo/odoo.conf"]
