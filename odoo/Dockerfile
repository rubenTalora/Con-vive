FROM odoo:17.0

# Install PyJWT for JWT authentication module
USER root
RUN pip3 install --no-cache-dir PyJWT==2.8.0

USER odoo

# Use custom config file
CMD ["odoo", "-c", "/etc/odoo/odoo.conf"]
