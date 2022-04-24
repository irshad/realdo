<script>
    export let text = '';
</script>

<div class="card">
    {@html text}
</div>

<style>
    .card {
        padding: 16px;
        font-size: 16px;
        font-weight: 800;
        font-family: 'Roboto Mono', monospace;
        border-radius: 14px;
        border: 2px solid var(--dark-color);
        background-color: var(--primary-color);
    }
</style>