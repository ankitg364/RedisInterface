<div class="col-lg-12">
    <h1>Edit Key</h1>

    <form name="form_addNewKey">

        <div class="col-lg-6">
            <input type="text" class="form-control" placeholder="Key" name="edit_key_addNewKey" id="key_addNewKey" style="font-size: 20" required>
        </div>
        <div class="col-lg-6">
            <input type="text" class="form-control" placeholder="" name="edit_opt_addNewKey" id="opt_addNewKey"
                   style="font-size: 20; visibility: hidden">
        </div>
        <br><br><br>
        <textarea rows="10" class="form-control" placeholder="Value" name="edit_value_addNewKey" id="value_addNewKey"
                  style="font-size: 20" required></textarea>
        <input type="text" style="display: none" name="address_addNewKey" id="address_addNewKey">

        <br>
        <button class="btn btn-default" type="submit">Edit</button>

    </form>
</div>