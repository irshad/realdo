<script>
    export let title = 'Nothing';
    export let highlight = 'Here!';
</script>

<div class="main-section">
    {title} <span>{highlight}</span>
</div>

<style>
    .main-section {
        display: flex;
        align-items: center;
        justify-content: center;
        height: 100%;
        font-size: 21px;
        font-weight: 800;
        font-family: 'Roboto Mono', monospace;
    }

    span {
        margin-left: 4px;
        padding: 0 3px 0 6px;
        border-radius: 8px;
        border: 2px solid var(--dark-color);
        background-color: var(--danger-color);
    }
</style>
