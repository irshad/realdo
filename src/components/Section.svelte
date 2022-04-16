<script>

</script>

<div class="section">
    <slot/>
</div>

<style>
    .section {
        padding: 16px;
        overflow-y: auto;
        height: calc(100% - 136px);
        border: 2px solid var(--dark-color);
        border-top: 0;
        border-bottom: 0;
    }
</style>